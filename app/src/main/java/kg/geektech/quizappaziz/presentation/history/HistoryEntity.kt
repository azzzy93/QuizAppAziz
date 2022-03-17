package kg.geektech.quizappaziz.presentation.history

data class HistoryEntity(
    val id: Int,
    val category: String,
    val correctAnswer: String,
    val difficulty: String,
    val date: String
)
