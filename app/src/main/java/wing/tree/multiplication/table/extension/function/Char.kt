@file:Suppress("unused")

package wing.tree.multiplication.table.extension.function

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

fun Char.repeat(n: Int): String = "$this".repeat(n)

@Composable
fun Char.width(style: TextStyle = LocalTextStyle.current): Dp = "$this".width(style)
