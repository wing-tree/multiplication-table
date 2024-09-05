@file:Suppress("unused")

package wing.tree.multiplication.table.extension

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import wing.tree.multiplication.table.extension.property.`2`

private val EIGHT = 8.0.dp
private val FOUR = 4.0.dp
private val SIXTEEN = 16.0.dp
private val TWELVE = 12.0.dp
private val TWENTY = 20.0.dp
private val TWENTY_FOUR = 24.0.dp
private val ZERO = 0.0.dp

val Dp.Companion.extraLarge: Dp get() = TWENTY_FOUR
val Dp.Companion.extraExtraSmall: Dp get() = FOUR
val Dp.Companion.large: Dp get() = TWENTY
val Dp.Companion.extraSmall: Dp get() = EIGHT
val Dp.Companion.medium: Dp get() = SIXTEEN
val Dp.Companion.small: Dp get() = TWELVE
val Dp.Companion.zero: Dp get() = ZERO

val Dp.half: Dp get() = div(Int.`2`)
