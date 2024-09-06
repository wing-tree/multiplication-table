package wing.tree.multiplication.table.speed.quiz.view.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.speed.quiz.action.SpeedQuizAction
import wing.tree.multiplication.table.speed.quiz.state.SpeedQuizState

@Composable
internal fun Played(
    state: SpeedQuizState.Play.Played,
    onAction: (SpeedQuizAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        state.speedQuiz.submission.forEach {
            Row {
                Text(text = it.timesTable.toString())
                Text("X")
                Text(text = it.multiplicand.toString())
                Text("=")
                Text(text = it.answer.value.toString())
            }
        }

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
