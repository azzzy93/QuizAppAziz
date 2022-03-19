package kg.geektech.quizappaziz.data.game.remote

import kg.geektech.quizappaziz.domain.game.entity.QuestionsEntityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {

    @GET("api.php")
    suspend fun getQsts(
        @Query("amount") amount: Int,
        @Query("category") categoryId: Int,
        @Query("difficulty") difficulty: String
    ): Response<QuestionsEntityResponse>

    @GET("api.php")
    suspend fun getQsts(
        @Query("amount") amount: Int,
        @Query("difficulty") difficulty: String
    ): Response<QuestionsEntityResponse>

    @GET("api.php")
    suspend fun getQsts(
        @Query("amount") amount: Int,
        @Query("category") categoryId: Int
    ): Response<QuestionsEntityResponse>

    @GET("api.php")
    suspend fun getQsts(
        @Query("amount") amount: Int
    ): Response<QuestionsEntityResponse>

}