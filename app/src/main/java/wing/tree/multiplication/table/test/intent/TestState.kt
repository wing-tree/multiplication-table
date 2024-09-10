package wing.tree.multiplication.table.test.intent

import androidx.compose.runtime.Stable
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.test.model.Test

@Stable
sealed interface TestState {
    val allAnswered: Boolean get() = test.all(Question::isAnswered)
    val contentKey: Boolean get() = this is Prepared

    val tag: Tag
    val test: Test

    sealed interface Prepared : TestState {
        data class Completed(
            override val test: Test,
            override val tag: Tag = Tag.COMPLETED
        ) : Prepared

        data class InProgress(override val test: Test) : Prepared {
            override val tag: Tag = Tag.IN_PROGRESS
        }
    }

    data class Preparing(override val test: Test) : TestState {
        override val tag: Tag = Tag.PREPARING
    }

    enum class Tag {
        COMPLETED,
        CLEARING,
        IN_PROGRESS,
        PREPARING;

        val isInProgress: Boolean get() = this `is` IN_PROGRESS
    }
}
