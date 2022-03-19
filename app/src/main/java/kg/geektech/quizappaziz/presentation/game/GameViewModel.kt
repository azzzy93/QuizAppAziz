package kg.geektech.quizappaziz.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.geektech.quizappaziz.core.BaseResult
import kg.geektech.quizappaziz.domain.game.entity.QuestionsEntity
import kg.geektech.quizappaziz.domain.game.usecase.GetQstsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val getQstsUseCase: GetQstsUseCase) : ViewModel() {

    private val _qstsList = MutableStateFlow<List<QuestionsEntity>>(mutableListOf())
    val qstsList: StateFlow<List<QuestionsEntity>> get() = _qstsList

    private val _state = MutableStateFlow<GameFragmentState>(GameFragmentState.Init)
    val state: StateFlow<GameFragmentState> get() = _state

    fun fetchQsts(
        amount: Int,
        categoryId: Int,
        difficulty: String
    ) {
        viewModelScope.launch {
            getQstsUseCase.invoke(amount, categoryId, difficulty)
                .onStart {
                    setLoading()
                }
                .catch {
                    hideLoading()
                    showToast(it.message ?: "Error")
                }
                .collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.Success -> {
                            _qstsList.value = it.data
                        }
                        is BaseResult.Error -> {
                            showToast(it.errorMsg)
                        }
                    }
                }
        }
    }

    private fun showToast(message: String) {
        _state.value = GameFragmentState.ShowToast(message)
    }

    private fun hideLoading() {
        _state.value = GameFragmentState.IsLoading(false)
    }

    private fun setLoading() {
        _state.value = GameFragmentState.IsLoading(true)
    }

    sealed class GameFragmentState {
        object Init : GameFragmentState()
        data class IsLoading(val isLoading: Boolean) : GameFragmentState()
        data class ShowToast(val message: String) : GameFragmentState()
    }

}