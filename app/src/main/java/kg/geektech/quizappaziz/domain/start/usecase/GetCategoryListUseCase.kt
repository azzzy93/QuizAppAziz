package kg.geektech.quizappaziz.domain.start.usecase

import kg.geektech.quizappaziz.domain.start.entity.CategoryEntity
import kg.geektech.quizappaziz.domain.start.repository.StartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(private val repository: StartRepository) {
    suspend fun invoke(): Flow<List<CategoryEntity>> {
        return repository.getCategoryList()
    }
}