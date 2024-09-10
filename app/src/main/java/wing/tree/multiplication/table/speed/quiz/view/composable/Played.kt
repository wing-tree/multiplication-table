package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.MultiplicationTableButton
import wing.tree.multiplication.table.composable.Prompt
import wing.tree.multiplication.table.extension.function.bounceVertically
import wing.tree.multiplication.table.extension.function.second
import wing.tree.multiplication.table.extension.function.verticalFadingEdge
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.empty
import wing.tree.multiplication.table.extension.property.fullyTransparent
import wing.tree.multiplication.table.extension.property.threeQuarters
import wing.tree.multiplication.table.speed.quiz.intent.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.intent.SpeedQuizState
import wing.tree.multiplication.table.test.view.composable.Score
import wing.tree.multiplication.table.theme.palette
import wing.tree.multiplication.table.theme.pastelGreen
import wing.tree.multiplication.table.theme.pastelRed
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.token.Space
import wing.tree.multiplication.table.top.level.function.containerColor
import wing.tree.multiplication.table.top.level.property.fillMaxWidth

@Composable
internal fun Played(
    state: SpeedQuizState.Play.Played,
    onAction: (SpeedQuizAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Space.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val speedQuiz = state.speedQuiz

        Score(submission = speedQuiz.submission)

        Column(
            modifier = fillMaxWidth.weight(weight = Float.`1`),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val scrollState = rememberScrollState()

            Column(
                modifier = fillMaxWidth
                    .verticalScroll(state = scrollState)
                    .verticalFadingEdge(scrollState = scrollState, length = Padding.extra.small)
                    .padding(vertical = Padding.small),
                verticalArrangement = Arrangement.spacedBy(space = Space.small)
            ) {
                speedQuiz.submission.forEach {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = containerColor(it.timesTable),
                            contentColor = colorScheme.onSurface
                        )
                    ) {
                        Row(
                            modifier = fillMaxWidth.padding(
                                horizontal = Padding.medium,
                                vertical = Padding.small
                            ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Prompt(
                                timesTable = it.timesTable,
                                multiplicand = it.multiplicand
                            )

                            Row(
                                modifier = Modifier.weight(Float.`1`),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val answer by it.answer
                                val color = when {
                                    it.isCorrect -> pastelGreen
                                    else -> pastelRed
                                }

                                Text(
                                    text = "${answer ?: String.empty}",
                                    modifier = Modifier
                                        .weight(Float.`1`)
                                        .background(color = colorScheme.surface)
                                        .padding(Padding.extra.small),
                                    color = color,
                                    maxLines = Int.`1`
                                )

                                Text(
                                    text = "${it.product}",
                                    modifier = Modifier
                                        .weight(weight = Float.threeQuarters)
                                        .padding(horizontal = Padding.extra.small)
                                        .graphicsLayer {
                                            if (it.isCorrect) {
                                                alpha = Float.fullyTransparent
                                            }
                                        },
                                    color = Color.Blue,
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = fillMaxWidth.bounceVertically(
                targetValue = Padding.extra.small
            ),
            verticalArrangement = Arrangement.spacedBy(space = Space.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MultiplicationTableButton(
                onClick = {
                    onAction(SpeedQuizAction.Home)
                },
                modifier = fillMaxWidth,
                containerColor = palette.first(),
                contentColor = colorScheme.onSurfaceVariant
            ) {
                Text(
                    text = stringResource(R.string.home),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            MultiplicationTableButton(
                onClick = {
                    onAction(SpeedQuizAction.SolveNew)
                },
                modifier = fillMaxWidth,
                containerColor = palette.second(),
                contentColor = colorScheme.onSurfaceVariant
            ) {
                Text(
                    text = stringResource(R.string.solve_new),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
