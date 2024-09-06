package wing.tree.multiplication.table.test.view.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.Icon
import wing.tree.multiplication.table.constant.NUMBER_OF_QUESTIONS
import wing.tree.multiplication.table.constant.PERFECT_SCORE
import wing.tree.multiplication.table.extension.function.shimmer
import wing.tree.multiplication.table.extension.small
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.test.model.StarRating
import wing.tree.multiplication.table.test.state.TestState
import wing.tree.multiplication.table.theme.pastelGreen

@Composable
internal fun Score(
    score: Int,
    modifier: Modifier = Modifier
) {
    val starRating = StarRating.get(score)

    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .shimmer()
                .padding(vertical = Dp.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.score),
                color = colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "$score",
                style = typography.displayMedium
            )

            Row {
                repeat(starRating.value) {
                    Icon(
                        id = R.drawable.round_grade_24,
                        tint = pastelGreen
                    )
                }
            }
        }
    }
}

@Composable
internal fun Score(
    state: TestState,
    modifier: Modifier = Modifier
) {
    val count = state.test.count(Question::isCorrect)
    val score = PERFECT_SCORE.div(NUMBER_OF_QUESTIONS).times(count)

    Score(
        score = score,
        modifier = modifier
    )
}
