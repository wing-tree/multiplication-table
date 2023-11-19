package wing.tree.multiplication.table.main.view.composable

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.Icon
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.quiz.view.composable.FloatingActionButton

@Composable
internal fun BottomBar(
    currentPage: Int,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        actions = {
            IconButton(
                onClick = {
                    onAction(Action.RateReview)
                }
            ) {
                Icon(id = R.drawable.round_rate_review_24)
            }

            IconButton(
                onClick = {
                    onAction(Action.Share)
                }
            ) {
                Icon(id = R.drawable.round_share_24)
            }
        },
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                currentPage = currentPage,
                onClick = {
                    onAction(Action.Quiz)
                }
            )
        }
    )
}
