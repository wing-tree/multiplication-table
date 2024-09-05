package wing.tree.multiplication.table.top.level

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.constant.EQUALS_SIGN
import wing.tree.multiplication.table.constant.MAXIMUM_MULTIPLICAND
import wing.tree.multiplication.table.constant.MULTIPLICATION_SIGN
import wing.tree.multiplication.table.extension.property.`4`
import wing.tree.multiplication.table.extension.property.`6`
import wing.tree.multiplication.table.extension.property.digit
import wing.tree.multiplication.table.extension.property.space
import wing.tree.multiplication.table.extension.maxWidth
import wing.tree.multiplication.table.extension.rememberWidth
import wing.tree.multiplication.table.extension.width

@Composable
fun multiplicationMaxWidth(style: TextStyle = LocalTextStyle.current): Dp {
    val maxWidth = Char.digit.maxWidth(style = style)
    val width = buildString {
        append(EQUALS_SIGN)
        append(MULTIPLICATION_SIGN)
        append(String.space.repeat(Int.`4`))
    }
        .width(style)
        .plus(maxWidth.times(Int.`6`))

    return width
}

@Composable
fun rememberMultiplicationMaxWidth(style: TextStyle = LocalTextStyle.current): Dp {
    val maxWidth = Char.digit.maxWidth(style = style)
    val width = buildString {
        append(EQUALS_SIGN)
        append(MULTIPLICATION_SIGN)
        append(String.space.repeat(Int.`4`))
    }
        .rememberWidth(style)
        .plus(maxWidth.times(Int.`6`))

    return remember(width) {
        width
    }
}

@Composable
fun rememberMultiplicationWidth(
    timesTable: Int,
    style: TextStyle = LocalTextStyle.current
): Dp {
    val maxWidth = Char.digit.maxWidth(style = style)
    val other = with("$timesTable") {
        val product = timesTable.times(MAXIMUM_MULTIPLICAND)

        maxWidth.times(
            other = length
                .plus(other = "$product".length)
                .plus(other = "$MAXIMUM_MULTIPLICAND".length)
        )
    }

    val width = buildString {
        append(EQUALS_SIGN)
        append(MULTIPLICATION_SIGN)
        append(String.space.repeat(n = Int.`4`))
    }
        .rememberWidth(style = style)
        .plus(other = other)

    return remember(width) {
        width
    }
}
