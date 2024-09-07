package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
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
import wing.tree.multiplication.table.composable.Prompt
import wing.tree.multiplication.table.extension.function.verticalFadingEdge
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.extension.property.empty
import wing.tree.multiplication.table.extension.property.fullyTransparent
import wing.tree.multiplication.table.extension.property.threeQuarters
import wing.tree.multiplication.table.speed.quiz.action.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.state.SpeedQuizState
import wing.tree.multiplication.table.test.view.composable.Score
import wing.tree.multiplication.table.theme.pastelGreen
import wing.tree.multiplication.table.theme.pastelRed
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.function.containerColor
import wing.tree.multiplication.table.top.level.property.fillMaxWidth
import wing.tree.multiplication.table.type.alias.Space

@Composable
internal fun Played(
    state: SpeedQuizState.Play.Played,
    onAction: (SpeedQuizAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = fillMaxWidth.weight(weight = Float.`1`),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val scrollState = rememberScrollState()
            val speedQuiz = state.speedQuiz

            Score(score = speedQuiz.submission.score)

            Column(
                modifier = fillMaxWidth
                    .verticalScroll(state = scrollState)
                    .verticalFadingEdge(scrollState = scrollState),
                verticalArrangement = Arrangement.spacedBy(space = Space.small)
            ) {
                speedQuiz.submission.forEach {
                    ElevatedCard(
                        colors = CardDefaults.elevatedCardColors(
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
            modifier = fillMaxWidth,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedButton(
                onClick = {
                    onAction(SpeedQuizAction.Home)
                }
            ) {
                Text(text = stringResource(R.string.home))
            }

            ElevatedButton(
                onClick = {
                    onAction(SpeedQuizAction.Replay)
                }
            ) {
                Text(text = stringResource(R.string.replay))
            }
        }
    }
}
