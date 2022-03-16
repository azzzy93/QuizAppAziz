package kg.geektech.quizappaziz.data.start

import kg.geektech.quizappaziz.data.start.remote.StartApi
import kg.geektech.quizappaziz.domain.start.entity.CategoryEntity
import kg.geektech.quizappaziz.domain.start.repository.StartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StartRepositoryImpl @Inject constructor(private val mainApi: StartApi) : StartRepository {

    override suspend fun getCategoryList(): Flow<List<CategoryEntity>> {
        return flow {
            val response = mainApi.getCategoryList()
            if (response.isSuccessful) {
                val body = response.body()
                body?.triviaCategories?.let {
                    emit(it)
                }
            }
        }
    }

}