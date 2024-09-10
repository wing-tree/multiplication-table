package wing.tree.multiplication.table.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.property.fillMaxWidth

@Composable
internal fun MultiplicationTableButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = FloatingActionButtonDefaults.shape
    ) {
        ProvideTextStyle(
            typography.bodyMedium.merge(fontWeight = FontWeight.Bold)
        ) {
            Box(
                modifier = fillMaxWidth
                    .padding(
                        horizontal = Padding.medium,
                        vertical = Padding.Compact.large
                    )
            ) {
                content()
            }
        }
    }
}
