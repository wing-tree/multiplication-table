package wing.tree.multiplication.table.test.view.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.extension.function.shimmer
import wing.tree.multiplication.table.extension.property.half
import wing.tree.multiplication.table.extension.property.slash
import wing.tree.multiplication.table.model.Question
import wing.tree.multiplication.table.model.Submission
import wing.tree.multiplication.table.test.intent.TestState
import wing.tree.multiplication.table.type.alias.Space

@Composable
internal fun Score(
    state: TestState,
    modifier: Modifier = Modifier
) {
    val test = state.test
    val score = test.count(Question::isCorrect)

    Score(
        text = "$score${String.slash}${test.count()}",
        modifier = modifier
    )
}

@Composable
internal fun Score(
    submission: Submission,
    modifier: Modifier = Modifier
) {
    val score = submission.score

    Score(
        text = "$score${String.slash}${submission.count()}",
        modifier = modifier
    )
}

@Composable
private fun Score(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.shimmer(),
        verticalArrangement = Arrangement.spacedBy(space = Space.large.half),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.score),
            color = colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = text,
            style = typography.headlineLarge
        )
    }
}
