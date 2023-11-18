package wing.tree.multiplication.table.main.view.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.Icon
import wing.tree.multiplication.table.extension.medium
import wing.tree.multiplication.table.extension.small
import wing.tree.multiplication.table.model.Action
import wing.tree.multiplication.table.quiz.view.composable.FloatingActionButton

@Composable
internal fun NavigationRail(
    currentPage: Int,
    onAction: (Action) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier,
        header = {
            FloatingActionButton(
                currentPage = currentPage,
                onClick = {
                    onAction(Action.Quiz)
                },
                modifier = Modifier.padding(vertical = Dp.medium)
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(
                space = Dp.small,
                alignment = Alignment.Bottom
            )
        ) {
            NavigationRailItem(
                selected = false,
                icon = {
                    Icon(id = R.drawable.round_rate_review_24)
                },
                onClick = {
                    onAction(Action.RateReview)
                }
            )

            NavigationRailItem(
                selected = false,
                icon = {
                    Icon(id = R.drawable.round_share_24)
                },
                onClick = {
                    onAction(Action.Share)
                }
            )

            NavigationRailItem(
                selected = false,
                icon = {
                    Icon(id = R.drawable.round_store_24)
                },
                onClick = {
                    onAction(Action.Store)
                }
            )
        }
    }
}
