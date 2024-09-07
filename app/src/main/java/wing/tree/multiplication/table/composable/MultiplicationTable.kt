package wing.tree.multiplication.table.composable

import android.text.TextPaint
import androidx.collection.LruCache
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.core.content.res.ResourcesCompat
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.extension.function.isLessThan
import wing.tree.multiplication.table.extension.function.isLessThanOrEqualTo
import wing.tree.multiplication.table.extension.function.width
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.`2`
import wing.tree.multiplication.table.extension.property.height
import wing.tree.multiplication.table.extension.property.inc
import wing.tree.multiplication.table.extension.property.widestDigit
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.function.containerColor
import wing.tree.multiplication.table.top.level.property.MAXIMUM_MULTIPLICAND
import wing.tree.multiplication.table.top.level.property.MAXIMUM_TIMES_TABLE_DIGITS
import wing.tree.multiplication.table.top.level.property.MINIMUM_MULTIPLICAND
import wing.tree.multiplication.table.top.level.property.fillMaxWidth
import wing.tree.multiplication.table.top.level.widestMultiplicationWidth

private val lruCache = LruCache<Pair<Dp, Dp>, TextUnit>(Int.`2`)

@Composable
fun MultiplicationTable(
    timesTable: Int,
    modifier: Modifier = Modifier
) {
    val shape = CardDefaults.elevatedShape

    ElevatedCard(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor(timesTable))
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .padding(horizontal = Padding.small)
                .padding(bottom = Padding.small)
        ) {
            val maxWidth = maxWidth
            val height = maxHeight
                .minus(Padding.large)
                .div(MAXIMUM_MULTIPLICAND.inc)

            val fontSize = fontSizeOf(
                maxWidth = maxWidth.minus(Padding.extra.small),
                maxHeight = height
            )

            val style = LocalTextStyle.current.copy(
                color = colorScheme.onSurface,
                fontSize = fontSize
            )

            val width = widestMultiplicationWidth(style)
            val widestDigitWidth = Char.widestDigit.width(style)

            Column(
                modifier = Modifier.width(width = width.plus(Padding.small)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.times_table, timesTable),
                    modifier = fillMaxWidth.padding(vertical = Padding.extra.small),
                    color = colorScheme.onSurfaceVariant,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = fillMaxWidth
                        .weight(weight = Float.`1`)
                        .background(color = colorScheme.surface)
                        .padding(horizontal = Padding.extra.small)
                        .padding(start = widestDigitWidth.times(MAXIMUM_TIMES_TABLE_DIGITS.minus("$timesTable".length))),
                    verticalArrangement = Arrangement.Center
                ) {
                    for (multiplicand in MINIMUM_MULTIPLICAND..MAXIMUM_MULTIPLICAND) {
                        Multiplication(
                            timesTable = timesTable,
                            multiplicand = multiplicand,
                            modifier = Modifier
                                .width(width = width)
                                .height(height = height),
                            style = style
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun fontSizeOf(
    maxWidth: Dp,
    maxHeight: Dp
): TextUnit = with(LocalDensity.current) {
    lruCache[maxWidth to maxHeight]?.let {
        return@with it
    }

    val context = LocalContext.current
    val textStyle = LocalTextStyle.current

    var textSize = Float.`1`
    val textPaint = TextPaint().also {
        it.typeface = ResourcesCompat.getFont(context, R.font.y_clover_regular)
        it.textSize = textSize
    }

    var height = textPaint.fontMetrics.height

    while (height isLessThanOrEqualTo maxHeight.toPx()) {
        textSize += Float.`1`

        textPaint.textSize = textSize

        height = textPaint.fontMetrics.height
    }

    var width = widestMultiplicationWidth(textStyle.copy(fontSize = textSize.toSp()))

    while (maxWidth isLessThan width) {
        textSize -= Float.`1`

        width = widestMultiplicationWidth(textStyle.copy(fontSize = textSize.toSp()))
    }

    return remember(maxWidth, maxHeight) {
        textSize.toSp().also {
            lruCache.put(maxWidth to maxHeight, it)
        }
    }
}
