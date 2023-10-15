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
                    // Proxy app error further
                    is AppError -> Failure(e)
//                    else -> throw IllegalStateException("Unhandled exception somewhere in outer code: ${e.stackTrace}")
                    else -> throw e
                }
            }
        }
    }
}