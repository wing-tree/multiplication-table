package wing.tree.multiplication.table.quiz.state

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import wing.tree.multiplication.table.model.Question

@Stable
sealed interface QuizState {
    val allAnswered: Boolean get() = quiz.all {
        it.answered
    }

    val quiz: ImmutableList<Question>
    val tag: Tag

    fun correct() = quiz.filter(Question::correct)

    data class Checked(override val quiz: ImmutableList<Question>) : QuizState {
        override val tag: Tag = Tag.CHECKED
    }

    data class Clearing(override val quiz: ImmutableList<Question>) : QuizState {
        override val tag: Tag = Tag.CLEARING
    }

    data class InProgress(override val quiz: ImmutableList<Question>) : QuizState {
        override val tag: Tag = Tag.IN_PROGRESS
    }

    enum class Tag {
        CHECKED,
        CLEARING,
        IN_PROGRESS
    }
}
