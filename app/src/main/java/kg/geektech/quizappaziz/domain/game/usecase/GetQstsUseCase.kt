package kg.geektech.quizappaziz.domain.game.usecase

import kg.geektech.quizappaziz.core.BaseResult
import kg.geektech.quizappaziz.domain.game.entity.QuestionsEntity
import kg.geektech.quizappaziz.domain.game.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQstsUseCase @Inject constructor(private val repository: GameRepository) {

    suspend fun invoke(
        amount: Int,
        categoryId: Int,
        difficulty: String
    ): Flow<BaseResult<List<QuestionsEntity>, String>> {
        return repository.getQsts(amount, categoryId, difficulty)
    }

    suspend fun invoke(
        amount: Int,
        difficulty: String
    ): Flow<BaseResult<List<QuestionsEntity>, String>> {
        return repository.getQsts(amount, difficulty)
    }

    suspend fun invoke(
        amount: Int,
        categoryId: Int
    ): Flow<BaseResult<List<QuestionsEntity>, String>> {
        return repository.getQsts(amount, categoryId)
    }

    suspend fun invoke(
        amount: Int
    ): Flow<BaseResult<List<QuestionsEntity>, String>> {
        return repository.getQsts(amount)
    }

}