package wing.tree.multiplication.table.quiz.model

import wing.tree.multiplication.table.constant.PERFECT_SCORE
import wing.tree.multiplication.table.extension.five
import wing.tree.multiplication.table.extension.four
import wing.tree.multiplication.table.extension.half
import wing.tree.multiplication.table.extension.`is`
import wing.tree.multiplication.table.extension.isGreaterThanOrEqualTo
import wing.tree.multiplication.table.extension.one
import wing.tree.multiplication.table.extension.quarter
import wing.tree.multiplication.table.extension.three
import wing.tree.multiplication.table.extension.threeQuarters
import wing.tree.multiplication.table.extension.two

enum class StarRating(val value: Int) {
    EXCELLENT(Int.three),
    GOOD(Int.one),
    GREAT(Int.two),
    OUTSTANDING(Int.four),
    PERFECT(Int.five);

    companion object {
        fun get(score: Int) = when {
            score `is` PERFECT_SCORE -> PERFECT
            score isGreaterThanOrEqualTo PERFECT_SCORE.threeQuarters -> OUTSTANDING
            score isGreaterThanOrEqualTo PERFECT_SCORE.half -> EXCELLENT
            score isGreaterThanOrEqualTo PERFECT_SCORE.quarter -> GREAT
            else -> GOOD
        }
    }
}
