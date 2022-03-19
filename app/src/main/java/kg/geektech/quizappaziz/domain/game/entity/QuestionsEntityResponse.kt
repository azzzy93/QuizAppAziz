package kg.geektech.quizappaziz.domain.game.entity

import com.google.gson.annotations.SerializedName

data class QuestionsEntityResponse(
    @SerializedName("response_code")
    val responseCode: Int?,
    val results: List<QuestionsEntity>?
)

data class QuestionsEntity(
    val category: String?,
    @SerializedName("correct_answer")
    val correctAnswer: String?,
    val difficulty: String?,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>?,
    val question: String?,
    val type: String?
)