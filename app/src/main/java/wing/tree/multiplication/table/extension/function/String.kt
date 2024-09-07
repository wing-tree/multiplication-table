@file:Suppress("unused")

package wing.tree.multiplication.table.extension.function

import android.text.TextPaint
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.core.content.res.ResourcesCompat
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.extension.property.`2`
import wing.tree.multiplication.table.extension.property.`3`
import wing.tree.multiplication.table.extension.property.secondIndex
import wing.tree.multiplication.table.extension.property.space

fun String.fourth() = get(Int.`3`)
fun String.second() = get(Int.secondIndex)
fun String.third() = get(Int.`2`)

@Composable
fun String.width(style: TextStyle = LocalTextStyle.current): Dp {
    val context = LocalContext.current
    val density = LocalDensity.current
    val textPaint = remember {
        TextPaint().also {
            it.typeface = ResourcesCompat.getFont(context, R.font.y_clover_regular)
        }
    }

    textPaint.textSize = with(density) {
        style.fontSize.toPx()
    }

    textPaint.letterSpacing = with(style) {
        letterSpacing.value.div(fontSize.value)
    }

    return remember(textPaint.textSize) {
        with(density) {
            textPaint.measureText(this@width).toDp()
        }
    }
}

fun String.wrapInSpaces() = "${String.space}$this${String.space}"
