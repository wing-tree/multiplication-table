package wing.tree.multiplication.table.composable

import android.text.TextPaint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.constant.EQUALS_SIGN
import wing.tree.multiplication.table.constant.MAXIMUM_MULTIPLICAND
import wing.tree.multiplication.table.constant.MINIMUM_MULTIPLICAND
import wing.tree.multiplication.table.constant.MULTIPLICATION_SIGN
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.medium
import wing.tree.multiplication.table.extension.property.digit
import wing.tree.multiplication.table.extension.rememberMaxWidth
import wing.tree.multiplication.table.top.level.containerColor
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
            val density = LocalDensity.current
            val height = maxHeight.div(other = MAXIMUM_MULTIPLICAND.inc())
            val fontSize = with(density) {
                height.times(0.7535F).toSp()
            }

            val style = LocalTextStyle.current.copy(fontSize = fontSize)
            val width = rememberMultiplicationWidth(
                timesTable = timesTable,
                style = style
            )

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
                        .width(IntrinsicSize.Max)
                        .background(
                            color = colorScheme.surface,
                            shape = shape
                        )
                        .padding(horizontal = Dp.extraSmall)
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
fun Dp.calculateFontSizeFromTotalHeight(): TextUnit = with(LocalDensity.current) {
    val textPaint = TextPaint()

    // 임의의 초기 폰트 크기
    var textSize = 20f
    textPaint.textSize = textSize

    // FontMetrics를 사용해 top과 bottom 차이를 계산
    var fontMetrics = textPaint.fontMetrics
    var totalHeight = fontMetrics.bottom - fontMetrics.top

    // 원하는 totalHeightWithPadding 값에 맞추기 위해 폰트 크기를 조정
    while (totalHeight <= toSp().toPx()) {
        textSize += 1f
        textPaint.textSize = textSize
        fontMetrics = textPaint.fontMetrics
        totalHeight = fontMetrics.bottom - fontMetrics.top

    }
    println("aaaa:$textSize,,,::$totalHeight")
    return textSize.toSp()
}