package wing.tree.multiplication.table.countdown.timer

import wing.tree.multiplication.table.extension.property.`-1`
import wing.tree.multiplication.table.extension.property.`0`
import wing.tree.multiplication.table.extension.property.isNegative
import wing.tree.multiplication.table.extension.property.isNonPositive
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

abstract class CountdownTimer(
    private val millisecondsInFuture: Long,
    private val countDownInterval: Long
) : Timer(true) {
    private var scheduledExecutionTime: Long = Long.`-1`
    private var timerTask: TimerTask? = null

    abstract fun onFinish()
    abstract fun onTick(millisecondsUntilFinished: Long)

    override fun cancel() {
        timerTask?.cancel()

        super.cancel()
    }

    private fun buildTimerTask(millisecondsInFuture: Long) = timerTask {
        val millisecondsUntilFinished: Long

        if (scheduledExecutionTime.isNegative) {
            scheduledExecutionTime = scheduledExecutionTime()
            millisecondsUntilFinished = millisecondsInFuture
        } else {
            millisecondsUntilFinished = millisecondsInFuture
                .minus(scheduledExecutionTime())
                .plus(scheduledExecutionTime)

            if (millisecondsUntilFinished.isNonPositive) {
                scheduledExecutionTime = Long.`-1`

                cancel()
                onFinish()

                return@timerTask
            }
        }

        onTick(millisecondsUntilFinished)
    }

    fun advanceTimeBy(time: Long) {
        scheduledExecutionTime -= time
    }

    fun start() {
        timerTask = buildTimerTask(millisecondsInFuture)

        schedule(timerTask, Long.`0`, countDownInterval)
    }

    fun stop() {
        scheduledExecutionTime = Long.`-1`
        timerTask?.cancel()

        timerTask = null
    }
}
