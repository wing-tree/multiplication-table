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
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.`2`
import wing.tree.multiplication.table.extension.property.`3`
import wing.tree.multiplication.table.extension.property.`4`
import wing.tree.multiplication.table.extension.property.`5`
import wing.tree.multiplication.table.extension.property.`6`

@Composable
fun List<Char>.maxWidth(style: TextStyle = LocalTextStyle.current): Dp {
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

    return with(density) {
        maxOf {
            textPaint.measureText("$it")
        }
            .toDp()
    }
}

fun <T> List<T>.fifth() = get(Int.`4`)
fun <T> List<T>.fourth() = get(Int.`3`)
fun <T> List<T>.second() = get(Int.`1`)
fun <T> List<T>.seventh() = get(Int.`6`)
fun <T> List<T>.sixth() = get(Int.`5`)
fun <T> List<T>.third() = get(Int.`2`)
