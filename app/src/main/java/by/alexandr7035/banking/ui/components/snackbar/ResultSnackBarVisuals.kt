package com.example.votekt.ui.components.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import by.alexandr7035.banking.ui.components.snackbar.SnackBarMode

data class ResultSnackBarVisuals(
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val message: String,
    override val withDismissAction: Boolean = false,
    val snackBarMode: SnackBarMode
) : SnackbarVisuals
