package wing.tree.multiplication.table.model

enum class Key {
    START, END_INCLUSIVE;

    operator fun invoke() = "$this"
}
