package wing.tree.multiplication.table.composable

import android.text.TextPaint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.constant.EQUALS_SIGN
import wing.tree.multiplication.table.constant.MAXIMUM_MULTIPLICAND
import wing.tree.multiplication.table.constant.MINIMUM_MULTIPLICAND
import wing.tree.multiplication.table.constant.MULTIPLICATION_SIGN
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.function.isLessThan
import wing.tree.multiplication.table.extension.function.isLessThanOrEqualTo
import wing.tree.multiplication.table.extension.half
import wing.tree.multiplication.table.extension.medium
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.height
import wing.tree.multiplication.table.top.level.containerColor
import wing.tree.multiplication.table.top.level.multiplicationMaxWidth
import wing.tree.multiplication.table.top.level.rememberMultiplicationMaxWidth
import wing.tree.multiplication.table.top.level.rememberMultiplicationWidth

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
                .padding(horizontal = Dp.extraSmall)
                .padding(bottom = Dp.extraSmall)
        ) {
            val maxWidth = maxWidth
            val height = maxHeight.minus(16.dp).div(other = MAXIMUM_MULTIPLICAND.inc())
            val fontSize = calculateFontSizeFromTotalHeight(height, maxWidth - 8.dp)

            val style = LocalTextStyle.current.copy(fontSize = fontSize)
            val width = rememberMultiplicationMaxWidth(style)
            val rwidth = rememberMultiplicationWidth(timesTable, style)
            Column(
                modifier = Modifier.width(width = width.plus(Dp.medium)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.times_table, timesTable),
                    modifier = Modifier.fillMaxWidth(),
                    color = colorScheme.onSurfaceVariant,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .width(width = maxWidth)
                        .weight(weight = Float.`1`)
                        .background(color = colorScheme.surface)
                        .padding(horizontal = Dp.extraSmall)
                        .padding(start = (width - rwidth).half),
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
private fun Multiplication(
    timesTable: Int,
    multiplicand: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    val product = timesTable.times(multiplicand)

    Text(
        text = "$timesTable $MULTIPLICATION_SIGN $multiplicand $EQUALS_SIGN $product",
        modifier = modifier,
        color = colorScheme.onSurface,
        style = style
    )
}

@Composable
fun calculateFontSizeFromTotalHeight(
    maxHeight: Dp,
    maxWidth: Dp
): TextUnit = with(LocalDensity.current) {
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

    var width = multiplicationMaxWidth(textStyle.copy(fontSize = textSize.toSp()))

    while (maxWidth isLessThan width) {
        textSize -= Float.`1`

        width = multiplicationMaxWidth(textStyle.copy(fontSize = textSize.toSp()))
    }

    return remember(maxWidth, maxHeight) {
        textSize.toSp()
    }
}
