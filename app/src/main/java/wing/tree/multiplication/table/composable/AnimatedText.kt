package wing.tree.multiplication.table.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import wing.tree.multiplication.table.extension.property.`3`
import wing.tree.multiplication.table.extension.property.empty
import wing.tree.multiplication.table.extension.property.hundreds

@Composable
internal fun AnimatedText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    transitionSpec: AnimatedContentTransitionScope<String>.() -> ContentTransform = {
        val towards = AnimatedContentTransitionScope.SlideDirection.Up
        val durationMillis = Int.`3`.hundreds
        val enter = fadeIn(animationSpec = tween(durationMillis = durationMillis))
            .plus(
                slideIntoContainer(
                    towards = towards,
                    animationSpec = tween(durationMillis = durationMillis)
                )
            )
        val exit = fadeOut(animationSpec = tween(durationMillis = durationMillis))
            .plus(
                slideOutOfContainer(
                    towards = towards,
                    animationSpec = tween(durationMillis = durationMillis)
                )
            )

        enter togetherWith exit
    },
    contentAlignment: Alignment = Alignment.TopStart,
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true
) {
    AnimatedContent(
        targetState = text,
        modifier = modifier,
        transitionSpec = transitionSpec,
        contentAlignment = contentAlignment,
        label = String.empty
    ) {

        Text(
            text = it,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            style = style
        )
    }
}
