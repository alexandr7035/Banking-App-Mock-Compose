package by.alexandr7035.banking.ui.app_host.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import by.alexandr7035.banking.ui.app_host.navigation.model.NavEntries
import by.alexandr7035.banking.ui.feature_login.LoginScreen

fun NavGraphBuilder.loginGraph(navController: NavController) {
    composable(NavEntries.Login.route) {
        LoginScreen(
            onLoginCompleted = {
                navController.navigate(route = NavEntries.Graphs.HomeGraph.route) {
                    popUpTo(NavEntries.Login.route) {
                        inclusive = true
                    }
                }
            },
            onGoToSignUp = {
                navController.navigate(NavEntries.Graphs.SignUpGraph.route) {
                    // TODO check ux
                    popUpTo(NavEntries.Login.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}