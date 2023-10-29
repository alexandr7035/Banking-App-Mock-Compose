package by.alexandr7035.banking.ui.app_host.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import by.alexandr7035.banking.ui.app_host.navigation.model.NavEntries
import by.alexandr7035.banking.ui.feature_app_lock.setup_applock.pin.CreatePinScreen

fun NavGraphBuilder.createAppLockGraph(
    navController: NavController
) {
    navigation(
        route = NavEntries.Graphs.CreateAppLock.route, startDestination = NavEntries.CreatePin.route
    ) {
        composable(NavEntries.CreatePin.route) {
            CreatePinScreen(
                onPinCreated = {
                    navController.navigate(NavEntries.Graphs.HomeGraph.route) {
                        popUpTo(NavEntries.Graphs.CreateAppLock.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}