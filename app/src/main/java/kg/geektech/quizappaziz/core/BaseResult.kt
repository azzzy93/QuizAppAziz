package kg.geektech.quizappaziz.core

sealed class BaseResult<out T : Any, out U : Any> {

    data class Success<T : Any>(val data: T) : BaseResult<T, Nothing>()
    data class Error<U : Any>(val errorMsg: U) : BaseResult<Nothing, U>()

}