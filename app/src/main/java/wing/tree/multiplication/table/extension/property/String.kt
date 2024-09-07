@file:Suppress("unused")

package wing.tree.multiplication.table.extension.property

import wing.tree.multiplication.table.extension.function.wrapInSpaces

val String.intOrNull: Int? get() = toIntOrNull()

val String.Companion.colon: String get() = ":"
val String.Companion.divisionSign: String get() = "÷"
val String.Companion.dot: String get() = "."
val String.Companion.empty: String get() = ""
val String.Companion.equalsSign: String get() = "="
val String.Companion.hyphen: String get() = "-"
val String.Companion.leftCurlyBrace: String get() = "{"
val String.Companion.new: String get() = "new"
val String.Companion.numberSign: String get() = "#"
val String.Companion.minusSign: String get() = "−"
val String.Companion.multiplicationSign: String get() = "×"
val String.Companion.plusSign: String get() = "+"
val String.Companion.questionMark: String get() = "?"
val String.Companion.rightCurlyBrace: String get() = "}"
val String.Companion.slash: String get() = "/"
val String.Companion.space: String get() = " "
val String.Companion.underScore: String get() = "_"
val String.Companion.widestPrompt: String get() = buildString {
    append(Char.widestDigit.twice)
    append(String.equalsSign.wrapInSpaces())
    append(Char.widestDigit)
    append(String.multiplicationSign.wrapInSpaces())
}
