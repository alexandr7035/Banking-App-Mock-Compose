package by.alexandr7035.banking.ui.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import by.alexandr7035.banking.ui.feature_login.LoginScreen
import by.alexandr7035.banking.ui.feature_wizard.WizardScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavHost(viewModel: AppViewModel = koinViewModel()) {
    val navController = rememberNavController()

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

    // TODO app routes model
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("wizard") {
            WizardScreen(
                onGoToLogin = {
                    navController.navigate("login")
                },
                onWizardCompleted = {
                    viewModel.setWizardViewed()
                }
            )
        }

        composable("login") {
            LoginScreen(onLoginCompleted = {
                viewModel.onLoginCompleted()

                navController.navigate("home") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            })
        }

        // TODO
        composable("home") {
            Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                Text(text = "Home")
            }
        }
    }
}