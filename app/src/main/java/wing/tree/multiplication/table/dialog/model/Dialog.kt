package wing.tree.multiplication.table.dialog.model

sealed interface Dialog {
    data class Progress(val message: String? = null) : Dialog
    data class Quiz(val start: Int, val endInclusive: Int) : Dialog
}
