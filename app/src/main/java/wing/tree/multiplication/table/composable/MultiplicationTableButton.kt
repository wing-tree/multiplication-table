package wing.tree.multiplication.table.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.extension.property.`16`
import wing.tree.multiplication.table.token.Padding
import wing.tree.multiplication.table.top.level.property.fillMaxWidth

@Composable
internal fun MultiplicationTableButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier.padding(vertical = Padding.small),
        shape = RoundedCornerShape(Dp.`16`)
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