package wing.tree.multiplication.table.dialog.model

sealed interface Dialog {
    data class Progress(val message: String? = null) : Dialog

    companion object : Dialog
}
