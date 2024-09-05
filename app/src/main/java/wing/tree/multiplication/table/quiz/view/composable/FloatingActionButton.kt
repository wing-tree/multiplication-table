package wing.tree.multiplication.table.quiz.view.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.extension.empty
import wing.tree.multiplication.table.extension.extraExtraSmall
import wing.tree.multiplication.table.extension.fourth
import wing.tree.multiplication.table.extension.half
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.`4`
import wing.tree.multiplication.table.extension.property.isEven
import wing.tree.multiplication.table.extension.property.negated
import wing.tree.multiplication.table.extension.second
import wing.tree.multiplication.table.extension.third
import wing.tree.multiplication.table.theme.palette

@Composable
internal fun FloatingActionButton(
    currentPage: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val content = @Composable {
        Content(
            currentPage = currentPage,
            onClick = onClick
        )
    }

    SubcomposeLayout(modifier = modifier) { constrains ->
        val floatingActionButton = @Composable {
            FloatingActionButton(
                onClick = {
                    noOperations
                },
                content = noOperations
            )
        }

        val (width, height) = subcompose(floatingActionButton, floatingActionButton).map {
            it.measure(constrains)
        }
            .first()
            .let {
                it.width to it.height
            }

        val placeable = subcompose(content, content).map {
            it.measure(Constraints(maxWidth = width, maxHeight = height))
        }
            .first()

        layout(width, height) {
            placeable.place(IntOffset.Zero)
        }
    }

}

@Composable
private fun Content(
    currentPage: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val text = stringResource(id = R.string.quiz)

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
    ) {
        val size = Int.`4`
        val colors = List(size) {
            val targetState = when {
                currentPage.isEven -> palette[it]
                else -> palette[it.plus(size)]
            }

            animateColorAsState(
                targetValue = targetState,
                label = String.empty
            )
                .value
        }

        Column {
            Row(
                modifier = Modifier.weight(Float.`1`),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(Float.`1`)
                        .background(colors.first()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${text.first()}",
                        modifier = Modifier.graphicsLayer {
                            with(Dp.extraExtraSmall.half) {
                                translationX = toPx()
                                translationY = toPx()
                            }
                        },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(Float.`1`)
                        .background(colors.second()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${text.second()}",
                        modifier = Modifier.graphicsLayer {
                            with(Dp.extraExtraSmall.half) {
                                translationX = toPx().negated
                                translationY = toPx()
                            }
                        },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(
                modifier = Modifier.weight(Float.`1`),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(Float.`1`)
                        .background(colors.third()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${text.third()}",
                        modifier = Modifier.graphicsLayer {
                            with(Dp.extraExtraSmall.half) {
                                translationX = toPx()
                                translationY = toPx().negated
                            }
                        },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(Float.`1`)
                        .background(colors.fourth()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${text.fourth()}",
                        modifier = Modifier.graphicsLayer {
                            with(Dp.extraExtraSmall.half) {
                                translationX = toPx().negated
                                translationY = toPx().negated
                            }
                        },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
