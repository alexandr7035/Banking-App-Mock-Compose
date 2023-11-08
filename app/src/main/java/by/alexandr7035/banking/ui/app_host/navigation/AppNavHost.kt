package by.alexandr7035.banking.ui.app_host.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.app_host.navigation.graphs.createAppLockGraph
import by.alexandr7035.banking.ui.app_host.navigation.graphs.loginGraph
import by.alexandr7035.banking.ui.app_host.navigation.graphs.signUpGraph
import by.alexandr7035.banking.ui.app_host.navigation.model.ConditionalNavigation
import by.alexandr7035.banking.ui.app_host.navigation.model.NavEntries
import by.alexandr7035.banking.ui.core.EnterScreenEffect
import by.alexandr7035.banking.ui.feature_account.action_topup.TopUpScreen
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.AddCardScreen
import by.alexandr7035.banking.ui.feature_cards.screen_card_details.CardDetailsScreen
import by.alexandr7035.banking.ui.feature_cards.screen_card_list.CardListScreen
import by.alexandr7035.banking.ui.feature_home.HomeScreen
import by.alexandr7035.banking.ui.feature_home.model.AccountAction
import by.alexandr7035.banking.ui.feature_onboarding.OnboardingScreen
import by.alexandr7035.banking.ui.feature_profile.ProfileScreen
import by.alexandr7035.banking.ui.feature_savings.SavingsScreen
import by.alexandr7035.banking.ui.feature_savings.screen_saving_details.SavingDetailsScreen
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
            navController.navigate(NavEntries.Login.route) {
                popUpTo(NavEntries.Graphs.HomeGraph.route) {
                    inclusive = true
                }
            }

            if (conditionalNavigation.requireOnboarding) {
                navController.navigate(NavEntries.Wizard.route) {
                    popUpTo(NavEntries.Login.route) {
                        inclusive = true
                    }
                }
            }
        } else {
            if (conditionalNavigation.requireCreateAppLock) {
                navController.navigate(NavEntries.Graphs.CreateAppLock.route) {
                    popUpTo(NavEntries.Graphs.HomeGraph.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavEntries.Graphs.HomeGraph.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(NavEntries.Wizard.route) {
            OnboardingScreen(
                onGoToLogin = {
                    navController.navigate(NavEntries.Login.route) {
                        popUpTo(NavEntries.Wizard.route) {
                            inclusive = true
                        }
                    }
                },
                onSignUp = {
                    navController.navigate(NavEntries.Graphs.SignUpGraph.route) {
                        popUpTo(NavEntries.Wizard.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        signUpGraph(navController)
        loginGraph(navController)
        createAppLockGraph(navController)

        navigation(
            startDestination = NavEntries.Home.route, route = NavEntries.Graphs.HomeGraph.route
        ) {
            composable(NavEntries.Home.route) {
                HomeScreen(
                    onGoToDestination = { navEntry ->
                        if (navEntry in listOf(
                                NavEntries.CardList, NavEntries.AddCard, NavEntries.SavingsList
                            )
                        ) {
                            navController.navigate(navEntry.route)
                        }
                    },
                    onCardDetails = { cardId ->
                        navController.navigate("${NavEntries.CardDetails.route}/${cardId}")
                    },
                    onSavingDetails = { id ->
                        navController.navigate("${NavEntries.SavingDetails.route}/${id}")
                    },
                    onAccountAction = {
                        when (it) {
                            AccountAction.TopUp -> {
                                navController.navigate(NavEntries.AccountTopUp.route)
                            }

                            AccountAction.Pay -> TODO()
                            AccountAction.RequestMoney -> TODO()
                            AccountAction.SendMoney -> TODO()
                        }
                    }
                )
            }

            composable(NavEntries.History.route) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = NavEntries.History.label,
                        textAlign = TextAlign.Center
                    )
                }
            }

            composable(NavEntries.Statistics.route) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = NavEntries.Statistics.label,
                        textAlign = TextAlign.Center
                    )
                }
            }

            composable(NavEntries.Profile.route) { navBackResult ->
                ProfileScreen(
                    onLogoutCompleted = {
                        navController.navigate(NavEntries.Login.route) {
                            popUpTo(NavEntries.Graphs.HomeGraph.route) {
                                inclusive = true
                            }
                        }
                    })
            }

            composable(NavEntries.CardList.route) {
                CardListScreen(
                    onAddCard = {
                        navController.navigate(NavEntries.AddCard.route)
                    },
                    onCardDetails = { cardId ->
                        navController.navigate("${NavEntries.CardDetails.route}/${cardId}")
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(NavEntries.AddCard.route) {
                AddCardScreen(onBack = {
                    navController.popBackStack()
                })
            }

            composable(
                route = "${NavEntries.CardDetails.route}/{cardId}", arguments = listOf(navArgument("cardId") { type = NavType.StringType })
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

                            }

                            AccountAction.TopUp -> {
                                val route = "${NavEntries.AccountTopUp.route}?selectedCard=${cardId}"
                                navController.navigate(route)
                            }
                        }
                    }
                )
            }

            composable(
                route = NavEntries.SavingsList.route
            ) {
                SavingsScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onSavingDetails = { id ->
                        navController.navigate("${NavEntries.SavingDetails.route}/${id}")
                    }
                )
            }

            composable(
                route = "${NavEntries.SavingDetails.route}/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) {
                SavingDetailsScreen(
                    savingId = it.arguments?.getLong("id")!!,
                    onBack = {
                        navController.popBackStack()
                    },
                    onLinkedCardDetails = { cardId ->
                        navController.navigate("${NavEntries.CardDetails.route}/${cardId}")
                    }
                )
            }

            composable(
                route = NavEntries.TermsAndConditions.route,
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

            composable(
                route = "${NavEntries.AccountTopUp.route}?selectedCard={selectedCard}",
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
        }
    }
}

