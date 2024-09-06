package wing.tree.multiplication.table.main.action

sealed interface MainAction {
    data object RateReview : MainAction
    data object Quiz : MainAction
    data object Share : MainAction

    sealed interface Navigate {
        data object ToSpeedQuiz : MainAction
        data object ToTest : MainAction
    }
}
