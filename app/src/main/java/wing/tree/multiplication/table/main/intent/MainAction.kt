package wing.tree.multiplication.table.main.intent

sealed interface MainAction {
    data object RateReview : MainAction
    data object Quiz : MainAction
    data object Share : MainAction

    sealed interface Navigate {
        data class ToSpeedQuiz(val start: Int, val endInclusive: Int) : MainAction
        data class ToTest(val start: Int, val endInclusive: Int) : MainAction
    }
}
