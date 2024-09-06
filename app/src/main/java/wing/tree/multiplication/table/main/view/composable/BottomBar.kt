package wing.tree.multiplication.table.main.view.composable

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.Icon
import wing.tree.multiplication.table.main.action.MainAction
import wing.tree.multiplication.table.test.view.composable.FloatingActionButton

@Composable
internal fun BottomBar(
    currentPage: Int,
    onAction: (MainAction) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        actions = {
            IconButton(
                onClick = {
                    onAction(MainAction.RateReview)
                }
            ) {
                Icon(id = R.drawable.round_rate_review_24)
            }

            IconButton(
                onClick = {
                    onAction(MainAction.Share)
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
                    onAction(MainAction.Quiz)
                }
            )
        }
    )
}
