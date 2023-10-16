package by.alexandr7035.banking.ui.core.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import by.alexandr7035.banking.ui.components.snackbar.SnackBarMode
import by.alexandr7035.banking.ui.components.snackbar.showResultSnackBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// App host coroutine scope may be passed here
// e.g. to preserve snackbar if user leaves screen where it triggered
data class ScopedSnackBarState(
    private val value: SnackbarHostState,
    private val coroutineScope: CoroutineScope
) {
    fun show(message: String, snackBarMode: SnackBarMode = SnackBarMode.Neutral) {
        coroutineScope.launch {
            value.showResultSnackBar(message, snackBarMode)
        }
    }
}

val LocalScopedSnackbarState = compositionLocalOf<ScopedSnackBarState> { error("No ScopedSnackBarState provided") }