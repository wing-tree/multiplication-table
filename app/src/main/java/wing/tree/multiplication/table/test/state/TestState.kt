package wing.tree.multiplication.table.test.state

import androidx.compose.runtime.Stable
import wing.tree.multiplication.table.test.model.Test

@Stable
sealed interface TestState {
    val isAllAnswered: Boolean get() = test.all {
        it.isAnswered
    }

    val tag: Tag
    val test: Test

    data class Completed(
        override val test: Test,
        override val tag: Tag = Tag.COMPLETED
    ) : TestState

    data class InProgress(override val test: Test) : TestState {
        override val tag: Tag = Tag.IN_PROGRESS
    }

    data class Preparing(override val test: Test) : TestState {
        override val tag: Tag = Tag.PREPARING
    }

    enum class Tag {
        COMPLETED,
        CLEARING,
        IN_PROGRESS,
        PREPARING
    }
}
