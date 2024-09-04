@file:Suppress("ObjectPropertyName", "unused")

package wing.tree.multiplication.table.extension.property

val Int.Companion.`0`: Int get() = 0
val Int.Companion.`1`: Int get() = 1
val Int.Companion.`11`: Int get() = 11
val Int.Companion.`12`: Int get() = 12
val Int.Companion.`13`: Int get() = 13
val Int.Companion.`2`: Int get() = 2
val Int.Companion.`3`: Int get() = 3
val Int.Companion.`4`: Int get() = 4
val Int.Companion.`5`: Int get() = 5
val Int.Companion.`6`: Int get() = 6
val Int.Companion.`7`: Int get() = 7
val Int.Companion.`8`: Int get() = 8
val Int.Companion.`9`: Int get() = 9
val Int.Companion.`-1`: Int get() = -1
val Int.Companion.firstIndex: Int get() = Int.`0`
val Int.Companion.none: Int get() = Int.`0`
val Int.Companion.oneHundred: Int get() = 100
val Int.Companion.oneThousand: Int get() = 1000
val Int.Companion.pair: Int get() = Int.`2`
val Int.Companion.secondIndex: Int get() = Int.`1`
val Int.Companion.single: Int get() = Int.`1`
val Int.Companion.ten: Int get() = 10
val Int.Companion.triple: Int get() = Int.`3`

private enum class Suffix {
    ND, RD, ST, TH;

    override fun toString(): String {
        return super.toString().lowercase()
    }
}
