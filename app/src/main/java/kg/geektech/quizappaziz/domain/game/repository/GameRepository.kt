package kg.geektech.quizappaziz.domain.game.repository

import kg.geektech.quizappaziz.core.BaseResult
import kg.geektech.quizappaziz.domain.game.entity.QuestionsEntity
import kotlinx.coroutines.flow.Flow


interface GameRepository {

    suspend fun getQsts(
        amount: Int,
        categoryId: Int,
        difficulty: String
    ): Flow<BaseResult<List<QuestionsEntity>, String>>

}