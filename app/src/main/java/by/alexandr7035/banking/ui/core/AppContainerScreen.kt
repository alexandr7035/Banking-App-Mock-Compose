package by.alexandr7035.banking.ui.core

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import by.alexandr7035.banking.ui.components.error.ErrorFullScreen
import by.alexandr7035.banking.ui.components.snackbar.ResultSnackBar
import by.alexandr7035.banking.ui.core.navigation.AppBottomNav
import by.alexandr7035.banking.ui.core.navigation.AppNavHost
import by.alexandr7035.banking.ui.core.navigation.LocalScopedSnackbarState
import by.alexandr7035.banking.ui.core.navigation.NavEntries
import by.alexandr7035.banking.ui.core.navigation.ScopedSnackBarState
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppContainerScreen(viewModel: AppViewModel = koinViewModel()) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val shouldShowBottomNav = NavEntries.primaryDestinations.contains(navBackStackEntry?.destination?.route)

    val snackBarHostState = remember { SnackbarHostState() }
    val hostCoroutineScope = rememberCoroutineScope()

    val state = viewModel.appState.collectAsStateWithLifecycle().value

    when (state) {
        is AppState.Loading -> {
            AppLoadingScreen()
        }

        is AppState.Ready -> {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState) { ResultSnackBar(snackbarData = it) }
                },
                bottomBar = { if (shouldShowBottomNav) AppBottomNav(navController) }
            ) { pv ->

                CompositionLocalProvider(
                    LocalScopedSnackbarState provides ScopedSnackBarState(
                        value = snackBarHostState,
                        coroutineScope = hostCoroutineScope
                    )
                ) {
                    AppNavHost(
                        navController = navController,
                        isLoggedIn = state.isLoggedIn,
                        hasPassedOnboarding = state.passedOnboarding
                    )
                }
            }
        }

        is AppState.InitFailure -> {
            ErrorFullScreen(
                error = state.error,
                onRetry = { viewModel.emitIntent(AppIntent.EnterApp) }
            )
        }
    }
}


@Composable
private fun AppReadyContent() {

}