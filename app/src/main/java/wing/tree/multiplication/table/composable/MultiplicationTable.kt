package wing.tree.multiplication.table.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import wing.tree.multiplication.table.R
import wing.tree.multiplication.table.constant.EQUALS_SIGN
import wing.tree.multiplication.table.constant.MAXIMUM_MULTIPLICAND
import wing.tree.multiplication.table.constant.MINIMUM_MULTIPLICAND
import wing.tree.multiplication.table.constant.MULTIPLICATION_SIGN
import wing.tree.multiplication.table.extension.extraSmall
import wing.tree.multiplication.table.extension.full
import wing.tree.multiplication.table.top.level.containerColor

@Composable
fun MultiplicationTable(
    timesTable: Int,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor(timesTable)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .padding(vertical = Dp.extraSmall)
            ) {
                Text(
                    text = stringResource(id = R.string.times_table, timesTable),
                    modifier = Modifier.fillMaxWidth(),
                    color = colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = typography.titleSmall
                )

                VerticalSpacer(height = Dp.extraSmall)

                for (multiplicand in MINIMUM_MULTIPLICAND..MAXIMUM_MULTIPLICAND) {
                    Multiplication(
                        timesTable = timesTable,
                        multiplicand = multiplicand,
                        modifier = Modifier.weight(Float.full)
                    )
                }
            }
        }
    }
}

@Composable
private fun Multiplication(
    timesTable: Int,
    multiplicand: Int,
    modifier: Modifier = Modifier
) {
    val product = timesTable.times(multiplicand)

    Text(
        text = "$timesTable $MULTIPLICATION_SIGN $multiplicand $EQUALS_SIGN $product",
        modifier = modifier,
        color = colorScheme.onSurface
    )
}
