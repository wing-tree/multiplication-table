package wing.tree.multiplication.table.main.action

sealed interface MainAction {
    data object RateReview : MainAction
    data object Quiz : MainAction
    data object Share : MainAction
}
