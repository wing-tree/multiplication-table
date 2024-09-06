package wing.tree.multiplication.table.test.model

import wing.tree.multiplication.table.constant.PERFECT_SCORE
import wing.tree.multiplication.table.extension.function.`is`
import wing.tree.multiplication.table.extension.function.isGreaterThanOrEqualTo
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.`2`
import wing.tree.multiplication.table.extension.property.`3`
import wing.tree.multiplication.table.extension.property.`4`
import wing.tree.multiplication.table.extension.property.`5`
import wing.tree.multiplication.table.extension.property.half
import wing.tree.multiplication.table.extension.property.quarter
import wing.tree.multiplication.table.extension.property.threeQuarters

enum class StarRating(val value: Int) {
    EXCELLENT(Int.`3`),
    GOOD(Int.`1`),
    GREAT(Int.`2`),
    OUTSTANDING(Int.`4`),
    PERFECT(Int.`5`);

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
