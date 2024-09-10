package wing.tree.multiplication.table.main.view.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.composable.Icon
import wing.tree.multiplication.table.main.intent.MainAction
import wing.tree.multiplication.table.test.view.composable.FloatingActionButton
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.property.fillMaxHeight

@Composable
internal fun NavigationRail(
    currentPage: Int,
    onAction: (MainAction) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier,
        header = {
            FloatingActionButton(
                currentPage = currentPage,
                onClick = {
                    onAction(MainAction.Quiz)
                },
                modifier = Modifier.padding(vertical = Padding.medium)
            )
        }
    ) {
        Column(
            modifier = fillMaxHeight,
            verticalArrangement = Arrangement.spacedBy(
                space = Padding.small,
                alignment = Alignment.Bottom
            )
        ) {
            NavigationRailItem(
                selected = false,
                icon = {
                    Icon(id = R.drawable.round_rate_review_24)
                },
                onClick = {
                    onAction(MainAction.RateReview)
                }
            )

            NavigationRailItem(
                selected = false,
                icon = {
                    Icon(id = R.drawable.round_share_24)
                },
                onClick = {
                    onAction(MainAction.Share)
                }
            )
        }
    }
}
