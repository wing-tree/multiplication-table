package wing.tree.multiplication.table.model

import androidx.annotation.FloatRange

@JvmInline
value class Percentage(@FloatRange(FROM, TO) val value: Float) {
    operator fun invoke() = value

    companion object {
        private const val FROM = 0.0
        private const val TO = 1.0
    }
}
