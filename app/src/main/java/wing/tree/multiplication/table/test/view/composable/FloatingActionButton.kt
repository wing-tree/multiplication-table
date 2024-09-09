package wing.tree.multiplication.table.test.view.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.IntOffset
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.extension.function.fourth
import wing.tree.multiplication.table.extension.function.second
import wing.tree.multiplication.table.extension.function.third
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.`4`
import wing.tree.multiplication.table.extension.property.empty
import wing.tree.multiplication.table.extension.property.isEven
import wing.tree.multiplication.table.extension.property.negated
import wing.tree.multiplication.table.theme.palette
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.property.fillMaxHeight

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

        val placeable = subcompose(
            slotId = content,
            content = content
        ).map {
            it.measure(Constraints(maxWidth = width, maxHeight = height))
        }
            .first()

        layout(width = width, height = height) {
            placeable.place(position = IntOffset.Zero)
        }
    }

}

@Composable
private fun Content(
    currentPage: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val text = stringResource(R.string.quiz)

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
            val translation = Padding.Compact.extra.small

            Row(
                modifier = Modifier.weight(weight = Float.`1`),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = fillMaxHeight
                        .weight(weight = Float.`1`)
                        .background(color = colors.first()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${text.first()}",
                        modifier = Modifier.graphicsLayer {
                            with(translation) {
                                translationX = toPx()
                                translationY = toPx()
                            }
                        },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = fillMaxHeight
                        .weight(Float.`1`)
                        .background(colors.second()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${text.second()}",
                        modifier = Modifier.graphicsLayer {
                            with(translation) {
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
                    modifier = fillMaxHeight
                        .weight(Float.`1`)
                        .background(colors.third()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${text.third()}",
                        modifier = Modifier.graphicsLayer {
                            with(translation) {
                                translationX = toPx()
                                translationY = toPx().negated
                            }
                        },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = fillMaxHeight
                        .weight(Float.`1`)
                        .background(colors.fourth()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${text.fourth()}",
                        modifier = Modifier.graphicsLayer {
                            with(translation) {
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
