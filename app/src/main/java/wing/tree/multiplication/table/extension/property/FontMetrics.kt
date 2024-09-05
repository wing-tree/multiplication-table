package wing.tree.multiplication.table.extension.property

import android.graphics.Paint.FontMetrics

val FontMetrics.height: Float get() = bottom.minus(top)
