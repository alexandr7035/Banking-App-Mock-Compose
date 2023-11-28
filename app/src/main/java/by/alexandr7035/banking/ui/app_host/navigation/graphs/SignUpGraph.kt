package by.alexandr7035.banking.ui.app_host.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import by.alexandr7035.banking.ui.app_host.navigation.model.NavDestinations
import by.alexandr7035.banking.ui.feature_signup.InitSignUpScreen
import by.alexandr7035.banking.ui.feature_signup.confirm_signup.ConfirmSignUpScreen
import by.alexandr7035.banking.ui.feature_signup.finish_signup.CompleteSignUpScreen

fun NavGraphBuilder.signUpGraph(navController: NavController) {
    navigation(
        route = NavDestinations.SignUpGraph.route,
        startDestination = NavDestinations.SignUpGraph.InitSignUp.route,
    ) {
        composable(route = NavDestinations.SignUpGraph.InitSignUp.route) {
            InitSignUpScreen(
                onGoToSignIn = {
                    navController.navigate(NavDestinations.Login.route) {
                        // TODO check ux
                        popUpTo(NavDestinations.SignUpGraph.InitSignUp.route) {
                            inclusive = true
                        }
                    }
                },
                onGoToConfirmSignUp = { email ->
                    navController.navigate("${NavDestinations.SignUpGraph.ConfirmSignUp.route}/${email}")
                },
                onGoToTermsAndConditions = {
                    navController.navigate(NavDestinations.TermsAndConditions.route)
                }
            )
        }

        composable(
            route = "${NavDestinations.SignUpGraph.ConfirmSignUp.route}/{otpDestination}",
            arguments = listOf(navArgument("otpDestination") { type = NavType.StringType })
        ) {
            val email = it.arguments?.getString("otpDestination")!!

            ConfirmSignUpScreen(
                email = email,
                onCodeVerified = {
                    navController.navigate(NavDestinations.SignUpGraph.CompletedSignUp.route) {
                        popUpTo(NavDestinations.SignUpGraph.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(NavDestinations.SignUpGraph.CompletedSignUp.route) {
            CompleteSignUpScreen(
                onClose = {
                    navController.navigate(NavDestinations.SetupAppLockGraph.route) {
                        // TODO check
                        popUpTo(NavDestinations.SignUpGraph.route) {
                            inclusive = true
                        }
                    }
                },
                onGoToTermsAndConditions = {
                    navController.navigate(NavDestinations.TermsAndConditions.route)
                }
            )
        }
    }
}