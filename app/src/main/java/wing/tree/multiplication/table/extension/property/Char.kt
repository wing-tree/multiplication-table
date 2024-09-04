@file:Suppress("ObjectPropertyName", "unused")

package wing.tree.multiplication.table.extension.property

val Char.Companion.`0`: Char get() = '0'
val Char.Companion.`1`: Char get() = '1'
val Char.Companion.`2`: Char get() = '2'
val Char.Companion.`3`: Char get() = '3'
val Char.Companion.`4`: Char get() = '4'
val Char.Companion.`5`: Char get() = '5'
val Char.Companion.`6`: Char get() = '6'
val Char.Companion.`7`: Char get() = '7'
val Char.Companion.`8`: Char get() = '8'
val Char.Companion.`9`: Char get() = '9'
val Char.Companion.blank: Char get() = ' '
val Char.Companion.digit: List<Char>
    get() = List(size = Int.ten, init = Int::digitToChar)

val Char.Companion.empty: Char get() = MIN_VALUE
val Char.Companion.equalsSign: Char get() = '='
val Char.Companion.multiplicationSign: Char get() = '×'
val Char.Companion.zeroWidthSpace: Char get() = '\u200B'
