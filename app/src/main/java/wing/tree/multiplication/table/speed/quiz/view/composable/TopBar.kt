package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import wing.tree.multiplication.table.composable.noOperations
import wing.tree.multiplication.table.extension.function.divAsFloat
import wing.tree.multiplication.table.speed.quiz.state.SpeedQuizState
import wing.tree.multiplication.table.speed.quiz.view.model.SpeedQuizViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun TopBar(
    state: SpeedQuizState,
    navigationOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = noOperations,
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = {
                    navigationOnClick()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            LinearProgressIndicator(
                progress = {
                    state.elapsedTime.divAsFloat(SpeedQuizViewModel.millisecondsInFuture)
                }
            )
        }
    )
}
