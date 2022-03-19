package kg.geektech.quizappaziz.data.game

import kg.geektech.quizappaziz.core.BaseResult
import kg.geektech.quizappaziz.data.game.remote.GameApi
import kg.geektech.quizappaziz.domain.game.entity.QuestionsEntity
import kg.geektech.quizappaziz.domain.game.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val gameApi: GameApi) : GameRepository {

    override suspend fun getQsts(
        amount: Int,
        categoryId: Int,
        difficulty: String
    ): Flow<BaseResult<List<QuestionsEntity>, String>> {
        return flow {
            val response = gameApi.getQsts(amount, categoryId, difficulty)
            if (response.isSuccessful) {
                val body = response.body()
                body?.results?.let {
                    emit(BaseResult.Success(it))
                }
            } else {
                emit(BaseResult.Error(response.message()))
            }
        }
    }

    override suspend fun getQsts(
        amount: Int,
        difficulty: String
    ): Flow<BaseResult<List<QuestionsEntity>, String>> {
        return flow {
            val response = gameApi.getQsts(amount, difficulty)
            if (response.isSuccessful) {
                val body = response.body()
                body?.results?.let {
                    emit(BaseResult.Success(it))
                }
            } else {
                emit(BaseResult.Error(response.message()))
            }
        }
    }

    override suspend fun getQsts(
        amount: Int,
        categoryId: Int
    ): Flow<BaseResult<List<QuestionsEntity>, String>> {
        return flow {
            val response = gameApi.getQsts(amount, categoryId)
            if (response.isSuccessful) {
                val body = response.body()
                body?.results?.let {
                    emit(BaseResult.Success(it))
                }
            } else {
                emit(BaseResult.Error(response.message()))
            }
        }
    }

    override suspend fun getQsts(
        amount: Int
    ): Flow<BaseResult<List<QuestionsEntity>, String>> {
        return flow {
            val response = gameApi.getQsts(amount)
            if (response.isSuccessful) {
                val body = response.body()
                body?.results?.let {
                    emit(BaseResult.Success(it))
                }
            } else {
                emit(BaseResult.Error(response.message()))
            }
        }
    }
}