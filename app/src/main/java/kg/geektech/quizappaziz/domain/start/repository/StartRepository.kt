package kg.geektech.quizappaziz.domain.start.repository

import kg.geektech.quizappaziz.domain.start.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface StartRepository {

    suspend fun getCategoryList(): Flow<List<CategoryEntity>>

}