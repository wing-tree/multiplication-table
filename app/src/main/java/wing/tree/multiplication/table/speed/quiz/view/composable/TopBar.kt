package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.CountdownTimer
import wing.tree.multiplication.table.extension.function.divAsFloat
import wing.tree.multiplication.table.extension.property.`1`
import wing.tree.multiplication.table.speed.quiz.intent.SpeedQuizState
import wing.tree.multiplication.table.speed.quiz.view.model.SpeedQuizViewModel
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.property.fillMaxWidth

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun TopBar(
    state: SpeedQuizState,
    navigationOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MediumTopAppBar(
        title = {
            Text(text = stringResource(R.string.speed_quiz))
        },
        modifier = modifier,
        actions = {
            Row(
                modifier = fillMaxWidth,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = navigationOnClick,
                    modifier = Modifier.padding(Padding.extra.small)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }

                Row(
                    modifier = Modifier.weight(weight = Float.`1`),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        progress = {
                            state.elapsedTime.divAsFloat(SpeedQuizViewModel.millisecondsInFuture)
                        },
                        modifier = Modifier.weight(weight = Float.`1`, fill = false),
                        strokeCap = StrokeCap.Round
                    )

                    CountdownTimer(
                        millisecondsUntilFinished = {
                            SpeedQuizViewModel.millisecondsInFuture.minus(state.elapsedTime)
                        },
                        modifier = Modifier.padding(horizontal = Padding.medium),
                        style = typography.bodyMedium
                    )
                }
            }
        }
    )
}
