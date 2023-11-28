package by.alexandr7035.banking.ui.app_host.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import by.alexandr7035.banking.ui.app_host.navigation.model.NavDestinations
import by.alexandr7035.banking.ui.feature_app_lock.setup_applock.biometrics.EnableBiometricsScreen
import by.alexandr7035.banking.ui.feature_app_lock.setup_applock.pin.CreatePinScreen

fun NavGraphBuilder.createAppLockGraph(
    navController: NavController
) {
    navigation(
        route = NavDestinations.SetupAppLockGraph.route,
        startDestination = NavDestinations.SetupAppLockGraph.CreatePin.route
    ) {
        val thisGraph = NavDestinations.SetupAppLockGraph.route

        composable(NavDestinations.SetupAppLockGraph.CreatePin.route) {
            CreatePinScreen(
                onPinCreated = { shouldRequestBiometrics ->
                    if (shouldRequestBiometrics) {
                        navController.navigate(NavDestinations.SetupAppLockGraph.EnableBiometrics.route) {
                            popUpTo(thisGraph) {
                                inclusive = true
                            }
                        }
                    }
                    else {
                        navController.navigate(NavDestinations.RootGraph.route) {
                            popUpTo(thisGraph) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }

        composable(NavDestinations.SetupAppLockGraph.EnableBiometrics.route) {
            EnableBiometricsScreen(
                onExit = {
                    navController.navigate(NavDestinations.RootGraph.route) {
                        popUpTo(thisGraph) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}