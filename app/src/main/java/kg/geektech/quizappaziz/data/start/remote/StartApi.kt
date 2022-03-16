package kg.geektech.quizappaziz.data.start.remote

import kg.geektech.quizappaziz.domain.start.entity.CategoryEntityResponse
import retrofit2.Response
import retrofit2.http.GET

interface StartApi {

    @GET("api_category.php")
    suspend fun getCategoryList(): Response<CategoryEntityResponse>

}