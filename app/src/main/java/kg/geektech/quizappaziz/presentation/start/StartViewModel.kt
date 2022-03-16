package kg.geektech.quizappaziz.presentation.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geektech.quizappaziz.domain.start.entity.CategoryEntity
import kg.geektech.quizappaziz.domain.start.usecase.GetCategoryListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase
) : ViewModel() {

    private val _categoryList = MutableStateFlow<List<CategoryEntity>>(mutableListOf())
    val categoryList: StateFlow<List<CategoryEntity>> get() = _categoryList

    init {
        fetchCategoryList()
    }

    private fun fetchCategoryList() {
        viewModelScope.launch {
            getCategoryListUseCase.invoke()
                .onStart {
                    setLoading()
                }
                .catch {
                    hindLoading()
                    showToast("Error")
                }
                .collect {
                    hindLoading()
                    _categoryList.value = it
                }
        }
    }

    private fun showToast(msg: String) {

    }

    private fun hindLoading() {

    }

    private fun setLoading() {

    }

}