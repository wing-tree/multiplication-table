package wing.tree.multiplication.table.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import wing.tree.multiplication.table.extension.function.verticalFadingEdge
import wing.tree.multiplication.table.extension.function.width
import wing.tree.multiplication.table.extension.property.`0`
import wing.tree.multiplication.table.extension.property.`2`
import wing.tree.multiplication.table.extension.property.colon
import wing.tree.multiplication.table.extension.property.widestDigit
import wing.tree.multiplication.table.token.Padding
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun CountdownTimer(
    millisecondsUntilFinished: () -> Long,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    val (minutes, seconds) = millisecondsUntilFinished()
        .milliseconds
        .toComponents { minutes, seconds, _ ->
            minutes to seconds
        }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Minutes(
            minutes = {
                minutes
            }
        )

        Text(
            text = String.colon,
            style = style
        )

        Seconds(
            seconds = {
                seconds
            }
        )
    }
}

@Composable
private fun Minutes(
    minutes: () -> Long,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    val text = "${minutes()}".padStart(
        length = Int.`2`,
        padChar = Char.`0`
    )

    val width = Char.widestDigit.width(style)

    Row(modifier = modifier) {
        text.forEach {
            AnimatedText(
                text = "$it",
                modifier = Modifier.width(width),
                contentAlignment = Alignment.Center,
                style = style.copy(textAlign = TextAlign.Center)
            )
        }
    }
}

@Composable
private fun Seconds(
    seconds: () -> Int,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    val text = "${seconds()}".padStart(
        length = Int.`2`,
        padChar = Char.`0`
    )

    val width = Char.widestDigit.width(style)

    Row(modifier = modifier) {
        text.forEach {
            AnimatedText(
                text = "$it",
                modifier = Modifier
                    .width(width)
                    .verticalFadingEdge(length = Padding.extra.small),
                contentAlignment = Alignment.Center,
                style = style.copy(textAlign = TextAlign.Center)
            )
        }
    }
}
