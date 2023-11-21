package by.alexandr7035.banking.ui.app_host.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.app_host.navigation.graphs.createAppLockGraph
import by.alexandr7035.banking.ui.app_host.navigation.graphs.signUpGraph
import by.alexandr7035.banking.ui.app_host.navigation.model.ConditionalNavigation
import by.alexandr7035.banking.ui.app_host.navigation.model.NavDestinations
import by.alexandr7035.banking.ui.core.effects.EnterScreenEffect
import by.alexandr7035.banking.ui.feature_account.action_send.SendMoneyScreen
import by.alexandr7035.banking.ui.feature_account.action_topup.TopUpScreen
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.AddCardScreen
import by.alexandr7035.banking.ui.feature_cards.screen_card_details.CardDetailsScreen
import by.alexandr7035.banking.ui.feature_cards.screen_card_list.CardListScreen
import by.alexandr7035.banking.ui.feature_home.HomeScreen
import by.alexandr7035.banking.ui.feature_account.components.account_actions.AccountAction
import by.alexandr7035.banking.ui.feature_app_settings.AppSettingsScreen
import by.alexandr7035.banking.ui.feature_help.HelpScreen
import by.alexandr7035.banking.ui.feature_login.LoginScreen
import by.alexandr7035.banking.ui.feature_onboarding.OnboardingScreen
import by.alexandr7035.banking.ui.feature_profile.ProfileScreen
import by.alexandr7035.banking.ui.feature_profile.menu.MenuEntry
import by.alexandr7035.banking.ui.feature_savings.SavingsScreen
import by.alexandr7035.banking.ui.feature_savings.screen_saving_details.SavingDetailsScreen
import by.alexandr7035.banking.ui.feature_transactions.TransactionHistoryScreen
import by.alexandr7035.banking.ui.feature_webview.WebViewScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    conditionalNavigation: ConditionalNavigation,
    paddingValues: PaddingValues
) {

    // Conditional navigation
    EnterScreenEffect() {
        if (conditionalNavigation.requireLogin) {
            navController.navigate(NavDestinations.Login.route) {
                popUpTo(NavDestinations.RootGraph.route) {
                    inclusive = true
                }
            }

            if (conditionalNavigation.requireOnboarding) {
                navController.navigate(NavDestinations.Onboarding.route) {
                    popUpTo(NavDestinations.Login.route) {
                        inclusive = true
                    }
                }
            }
        } else {
            if (conditionalNavigation.requireCreateAppLock) {
                navController.navigate(NavDestinations.SetupAppLockGraph.route) {
                    popUpTo(NavDestinations.RootGraph.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavDestinations.RootGraph.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        signUpGraph(navController)
        createAppLockGraph(navController)

        composable(NavDestinations.Login.route) {
            LoginScreen(
                onLoginCompleted = {
                    navController.navigate(route = NavDestinations.SetupAppLockGraph.route) {
                        popUpTo(NavDestinations.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onGoToSignUp = {
                    navController.navigate(NavDestinations.SignUpGraph.route) {
                        // TODO check ux (onboarding)
                        popUpTo(NavDestinations.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(NavDestinations.Onboarding.route) {
            OnboardingScreen(
                onGoToLogin = {
                    navController.navigate(NavDestinations.Login.route) {
                        popUpTo(NavDestinations.Onboarding.route) {
                            inclusive = true
                        }
                    }
                },
                onSignUp = {
                    navController.navigate(NavDestinations.SignUpGraph.route) {
                        popUpTo(NavDestinations.Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = NavDestinations.TermsAndConditions.route,
        ) {
            val title = stringResource(id = R.string.terms_and_conditions)
            val url = "https://example.com"

            WebViewScreen(
                title = title,
                url = url,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        navigation(
            startDestination = NavDestinations.RootGraph.Home.route,
            route = NavDestinations.RootGraph.route
        ) {
            composable(NavDestinations.RootGraph.Home.route) {
                HomeScreen(
                    onGoToDestination = { navEntry ->
                        if (navEntry in listOf(
                                NavDestinations.RootGraph.CardList,
                                NavDestinations.RootGraph.AddCard,
                                NavDestinations.RootGraph.SavingsList
                            )
                        ) {
                            navController.navigate(navEntry.route)
                        }
                    },
                    onCardDetails = { cardId ->
                        val route = NavDestinations.RootGraph.CardDetails.route
                        navController.navigate("${route}/${cardId}")
                    },
                    onSavingDetails = { id ->
                        val route = NavDestinations.RootGraph.SavingDetails.route
                        navController.navigate("${route}/${id}")
                    },
                    onAccountAction = {
                        when (it) {
                            AccountAction.TopUp -> {
                                navController.navigate(NavDestinations.RootGraph.AccountTopUp.route)
                            }

                            AccountAction.SendMoney -> {
                                navController.navigate(NavDestinations.RootGraph.AccountSend.route)
                            }

                            // TODO
                            AccountAction.Pay -> {}
                            AccountAction.RequestMoney -> {}
                        }
                    }
                )
            }

            composable(NavDestinations.RootGraph.TransactionHistory.route) {
                TransactionHistoryScreen()
            }

            composable(NavDestinations.RootGraph.Profile.route) { navBackResult ->
                ProfileScreen(
                    onLogoutCompleted = {
                        navController.navigate(NavDestinations.Login.route) {
                            popUpTo(NavDestinations.RootGraph.route) {
                                inclusive = true
                            }
                        }
                    },
                    onMenuEntry = {
                        val route = when (it) {
                            MenuEntry.Help -> NavDestinations.RootGraph.Help.route
                            MenuEntry.AppSettings -> NavDestinations.RootGraph.AppSettings.route
                            else -> error("No route specified for setting $it")
                        }

                        navController.navigate(route)
                    }
                )
            }

            composable(NavDestinations.RootGraph.CardList.route) {
                CardListScreen(
                    onAddCard = {
                        navController.navigate(NavDestinations.RootGraph.AddCard.route)
                    },
                    onCardDetails = { cardId ->
                        navController.navigate("${NavDestinations.RootGraph.CardDetails.route}/${cardId}")
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(NavDestinations.RootGraph.AddCard.route) {
                AddCardScreen(onBack = {
                    navController.popBackStack()
                })
            }

            composable(
                route = "${NavDestinations.RootGraph.CardDetails.route}/{cardId}",
                arguments = listOf(navArgument("cardId") { type = NavType.StringType })
            ) { it ->
                val cardId = it.arguments?.getString("cardId")!!

                CardDetailsScreen(
                    cardId = cardId,
                    onBack = {
                        navController.popBackStack()
                    },
                    onAccountAction = { action ->
                        when (action) {
                            AccountAction.Pay -> {

                            }

                            AccountAction.RequestMoney -> {

                            }

                            AccountAction.SendMoney -> {
                                val route = "${NavDestinations.RootGraph.AccountSend.route}?selectedCard=${cardId}"
                                navController.navigate(route)
                            }

                            AccountAction.TopUp -> {
                                val route = "${NavDestinations.RootGraph.AccountTopUp.route}?selectedCard=${cardId}"
                                navController.navigate(route)
                            }
                        }
                    }
                )
            }

            composable(
                route = NavDestinations.RootGraph.SavingsList.route
            ) {
                SavingsScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onSavingDetails = { id ->
                        navController.navigate("${NavDestinations.RootGraph.SavingDetails.route}/${id}")
                    }
                )
            }

            composable(
                route = "${NavDestinations.RootGraph.SavingDetails.route}/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) {
                SavingDetailsScreen(
                    savingId = it.arguments?.getLong("id")!!,
                    onBack = {
                        navController.popBackStack()
                    },
                    onLinkedCardDetails = { cardId ->
                        navController.navigate("${NavDestinations.RootGraph.CardDetails.route}/${cardId}")
                    }
                )
            }

            composable(
                route = "${NavDestinations.RootGraph.AccountTopUp.route}?selectedCard={selectedCard}",
                arguments = listOf(
                    navArgument("selectedCard") {
                        nullable = true
                        defaultValue = null
                        type = NavType.StringType
                    }
                )
            ) {
                val selectedCard = it.arguments?.getString("selectedCard")

                TopUpScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    selectedCardId = selectedCard
                )
            }

            composable(
                route = "${NavDestinations.RootGraph.AccountSend.route}?selectedCard={selectedCard}",
                arguments = listOf(
                    navArgument("selectedCard") {
                        nullable = true
                        defaultValue = null
                        type = NavType.StringType
                    }
                )
            ) {
                val selectedCard = it.arguments?.getString("selectedCard")

                SendMoneyScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    selectedCardId = selectedCard
                )
            }

            composable(NavDestinations.RootGraph.Help.route) {
                HelpScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(NavDestinations.RootGraph.AppSettings.route) {
                AppSettingsScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

