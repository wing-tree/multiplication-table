package wing.tree.multiplication.table.model

class Submission : MutableList<Question> by mutableListOf() {
    val score: Int get() = count(Question::isCorrect)
}
