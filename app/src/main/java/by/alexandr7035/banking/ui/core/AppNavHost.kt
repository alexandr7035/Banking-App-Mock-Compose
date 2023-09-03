package by.alexandr7035.banking.ui.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import by.alexandr7035.banking.ui.components.snackbar.ResultSnackBar
import by.alexandr7035.banking.ui.components.snackbar.showResultSnackBar
import by.alexandr7035.banking.ui.feature_login.LoginScreen
import by.alexandr7035.banking.ui.feature_wizard.WizardScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(viewModel: AppViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val hostCoroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (!viewModel.isLoggedIn()) {
            navController.navigate("login") {
                popUpTo("home") {
                    inclusive = true
                }
            }

            if (!viewModel.isWizardViewed()) {
                navController.navigate("wizard") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { ResultSnackBar(snackbarData = it) }
        })
    { pv ->
        // TODO app routes model
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(pv)
        ) {
            composable("wizard") {
                WizardScreen(onGoToLogin = {
                    navController.navigate("login")
                }, onWizardCompleted = {
                    viewModel.setWizardViewed()
                })
            }

            composable("login") {
                LoginScreen(
                    onLoginCompleted = {
                        viewModel.onLoginCompleted()

                        navController.navigate("home") {
                            popUpTo("login") {
                                inclusive = true
                            }
                        }
                    },
                    onShowSnackBar = { msg, mode ->
                        hostCoroutineScope.launch {
                            snackBarHostState.showResultSnackBar(msg, mode)
                        }
                    })
            }

            // TODO
            composable("home") {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Home")
                }
            }
        }
    }

}