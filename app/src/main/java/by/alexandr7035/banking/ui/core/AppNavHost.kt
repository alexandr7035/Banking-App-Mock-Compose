package by.alexandr7035.banking.ui.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import by.alexandr7035.banking.ui.feature_wizard.WizardScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "wizard"
    ) {
        composable("wizard") {
            WizardScreen()
        }
    }
}