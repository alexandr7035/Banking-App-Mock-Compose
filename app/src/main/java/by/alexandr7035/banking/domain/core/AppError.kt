package by.alexandr7035.banking.domain.core

import java.io.IOException

data class AppError(val errorType: ErrorType): IOException()