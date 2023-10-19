package by.alexandr7035.banking.ui.app_host.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import by.alexandr7035.banking.ui.app_host.navigation.model.NavEntries
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.AddCardScreen
import by.alexandr7035.banking.ui.feature_cards.screen_card_details.CardDetailsScreen
import by.alexandr7035.banking.ui.feature_cards.screen_card_list.CardListScreen
import by.alexandr7035.banking.ui.feature_home.components.HomeScreen
import by.alexandr7035.banking.ui.feature_login.LoginScreen
import by.alexandr7035.banking.ui.feature_onboarding.OnboardingScreen
import by.alexandr7035.banking.ui.feature_profile.ProfileScreen
import by.alexandr7035.banking.ui.feature_savings.SavingsScreen

// TODO split nav graph
@Composable
fun AppNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    hasPassedOnboarding: Boolean,
    paddingValues: PaddingValues
) {

    // Conditional navigation
    LaunchedEffect(isLoggedIn, hasPassedOnboarding) {
        if (!isLoggedIn) {
            navController.navigate(NavEntries.Login.route) {
                popUpTo(NavEntries.Graphs.HomeGraph.route) {
                    inclusive = true
                }
            }

            if (!hasPassedOnboarding) {
                navController.navigate(NavEntries.Wizard.route) {
                    popUpTo(NavEntries.Login.route) {
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
                    navController.navigate(NavEntries.Login.route)
                },
                onSignUp = {
                    // TODO
                }
            )
        }

        composable(NavEntries.Login.route) {
            LoginScreen(onLoginCompleted = {
                navController.navigate(NavEntries.Graphs.HomeGraph.route) {
                    popUpTo(NavEntries.Login.route) {
                        inclusive = true
                    }
                }
            })
        }

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
            ) {
                CardDetailsScreen(
                    cardId = it.arguments?.getString("cardId")!!,
                    onBack = {
                        navController.popBackStack()
                    },
                )
            }

            composable(
                route = NavEntries.SavingsList.route
            ) {
                SavingsScreen(onBack = {
                    navController.popBackStack()
                })
            }
        }
    }
}

