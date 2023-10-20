package by.alexandr7035.banking.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope

// Do not make here any work that requires duration, e.g. API call
// As if configurationChange happens during that work, the state may be lost
// Instead, pass some intent to ViewModel
//
// If some drawbacks are found, just comment the isCompleted condition and use it as a regular launced effect
@Composable
fun EnterScreenEffect(
    key1: Any?,
    block: suspend CoroutineScope.() -> Unit
) {
    val isCompleted = rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = key1, block = {
        if (!isCompleted.value) {
            block()
            isCompleted.value = true
        }
    })
}