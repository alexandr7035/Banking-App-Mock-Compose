package by.alexandr7035.banking.ui.app_host.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import by.alexandr7035.banking.ui.app_host.navigation.model.NavEntries
import by.alexandr7035.banking.ui.feature_signup.InitSignUpScreen
import by.alexandr7035.banking.ui.feature_signup.confirm_signup.ConfirmSignUpScreen
import by.alexandr7035.banking.ui.feature_signup.finish_signup.CompleteSignUpScreen

fun NavGraphBuilder.signUpGraph(navController: NavController) {
    navigation(
        startDestination = NavEntries.InitSignUp.route,
        route = NavEntries.Graphs.SignUpGraph.route
    ) {
        composable(route = NavEntries.InitSignUp.route) {
            InitSignUpScreen(
                onGoToSignIn = {
                    navController.navigate(NavEntries.Login.route) {
                        // TODO check ux
                        popUpTo(NavEntries.InitSignUp.route) {
                            inclusive = true
                        }
                    }
                },
                onGoToConfirmSignUp = { email ->
                    navController.navigate("${NavEntries.ConfirmSignUp.route}/${email}")
                },
                onGoToTermsAndConditions = {
                    navController.navigate(NavEntries.TermsAndConditions.route)
                }
            )
        }

        composable(
            route = "${NavEntries.ConfirmSignUp.route}/{otpDestination}",
            arguments = listOf(navArgument("otpDestination") { type = NavType.StringType })
        ) {
            val email = it.arguments?.getString("otpDestination")!!

            ConfirmSignUpScreen(
                email = email,
                onCodeVerified = {
                    navController.navigate(NavEntries.CompletedSignUp.route) {
                        popUpTo(NavEntries.Graphs.SignUpGraph.route) {
//                            popUpTo("${NavEntries.ConfirmSignUp.route}/${email}") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(NavEntries.CompletedSignUp.route) {
            CompleteSignUpScreen(
                onClose = {
                    navController.navigate(NavEntries.Graphs.CreateAppLock.route) {
                        // TODO check
                        popUpTo(NavEntries.Graphs.SignUpGraph.route) {
                            inclusive = true
                        }
                    }
                },
                onGoToTermsAndConditions = {
                    navController.navigate(NavEntries.TermsAndConditions.route)
                }
            )
        }
    }
}