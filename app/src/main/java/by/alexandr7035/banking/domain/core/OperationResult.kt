package by.alexandr7035.banking.domain.core

sealed class OperationResult<out T> {
    data class Success<out T>(val data: T) : OperationResult<T>()

    data class Failure(val error: AppError) : OperationResult<Nothing>()

    fun isSuccess(): Boolean {
        return when (this) {
            is Success -> true
            is Failure -> false
        }
    }

    companion object {
        inline fun <R> runWrapped(block: () -> R): OperationResult<R> {
            return try {
                val res = block()
                Success(res)
            } catch (e: Exception) {
                when (e) {
                    is AppError -> Failure(e)
                    else -> Failure(AppError(ErrorType.fromThrowable(e)))
                }
            }
        }
    }
}