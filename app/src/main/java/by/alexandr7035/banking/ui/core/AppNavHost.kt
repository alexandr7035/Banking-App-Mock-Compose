package by.alexandr7035.banking.ui.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import by.alexandr7035.banking.ui.components.snackbar.ResultSnackBar
import by.alexandr7035.banking.ui.components.snackbar.showResultSnackBar
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.AddCardScreen
import by.alexandr7035.banking.ui.feature_cards.screen_card_list.CardListScreen
import by.alexandr7035.banking.ui.feature_home.components.HomeScreen
import by.alexandr7035.banking.ui.feature_login.LoginScreen
import by.alexandr7035.banking.ui.feature_profile.ProfileScreen
import by.alexandr7035.banking.ui.feature_profile.logout_dialog.LogoutDialog
import by.alexandr7035.banking.ui.feature_wizard.WizardScreen
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavHost(viewModel: AppViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    val hostCoroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (!viewModel.isLoggedIn()) {
            navController.navigate(NavEntries.Login.route) {
                popUpTo(NavEntries.Graphs.HomeGraph.route) {
                    inclusive = true
                }
            }

            if (!viewModel.isWizardViewed()) {
                navController.navigate(NavEntries.Wizard.route) {
                    popUpTo(NavEntries.Login.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val shouldShowBottomNav = NavEntries.primaryDestinations.contains(navBackStackEntry?.destination?.route)
//    val shouldShowBottomNav = false

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) { ResultSnackBar(snackbarData = it) }
    }, bottomBar = { if (shouldShowBottomNav) BottomNav(navController) }) { pv ->
        // TODO app routes model
        NavHost(
            navController = navController, startDestination = NavEntries.Graphs.HomeGraph.route, modifier = Modifier.padding(pv)
        ) {
            composable(NavEntries.Wizard.route) {
                WizardScreen(onGoToLogin = {
                    navController.navigate(NavEntries.Login.route)
                }, onWizardCompleted = {
                    viewModel.setWizardViewed()
                })
            }

            composable(NavEntries.Login.route) {
                LoginScreen(onLoginCompleted = {
                    viewModel.onLoginCompleted()

                    navController.navigate(NavEntries.Graphs.HomeGraph.route) {
                        popUpTo(NavEntries.Login.route) {
                            inclusive = true
                        }
                    }
                }, onShowSnackBar = { msg, mode ->
                    hostCoroutineScope.launch {
                        snackBarHostState.showResultSnackBar(msg, mode)
                    }
                })
            }

            navigation(
                startDestination = NavEntries.Home.route, route = NavEntries.Graphs.HomeGraph.route
            ) {
                composable(NavEntries.Home.route) {
                    HomeScreen(onGoToDestination = { navEntry ->
                        if (navEntry in listOf(
                                NavEntries.CardList
                            // TODO other destinations
                            )
                        ) {
                            navController.navigate(navEntry.route)
                        }
                    })
                }

                composable(NavEntries.History.route) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = NavEntries.History.label, textAlign = TextAlign.Center
                        )
                    }
                }

                composable(NavEntries.Statistics.route) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = NavEntries.Statistics.label, textAlign = TextAlign.Center
                        )
                    }
                }

                composable(NavEntries.Profile.route) { navBackResult ->
                    ProfileScreen(
                        onLogoutClick = {
                            navController.navigate(NavEntries.LogoutDialog.route)
                        }
                    )

                    // logout dialog result
                    val shouldTryLogout = navBackResult.savedStateHandle.get<Boolean>(NavResult.SHOULD_LOGOUT.name) ?: false
                    LaunchedEffect(shouldTryLogout) {
                        if (shouldTryLogout) {
                            viewModel.logOut()

                            navController.navigate(NavEntries.Login.route) {
                                popUpTo(NavEntries.Graphs.HomeGraph.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }

                dialog(route = NavEntries.LogoutDialog.route) {
                    LogoutDialog(onDismiss = { shouldLogout ->
                        navController.previousBackStackEntry?.savedStateHandle?.set(NavResult.SHOULD_LOGOUT.name, shouldLogout)
                        navController.popBackStack()
                    })
                }

                composable(NavEntries.CardList.route) {
                    CardListScreen(
                        onAddCard = {
                            navController.navigate(NavEntries.AddCard.route)
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
            }
        }
    }

}


@Composable
private fun BottomNav(navController: NavController) {
    val destinationsWithBottomNav = listOf(
        NavEntries.Home,
        NavEntries.History,
        NavEntries.Statistics,
        NavEntries.Profile,
    )

    BottomNavigation(
        modifier = Modifier
            .height(
                72.dp
            )
            .shadow(elevation = 24.dp, spotColor = Color(0x08000000), ambientColor = Color(0x08000000)),
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        destinationsWithBottomNav.forEach { navEntry ->
            val isDestinationSelected = currentDestination?.hierarchy?.any { it.route == navEntry.route } == true

            val icon = if (isDestinationSelected) {
                painterResource(id = navEntry.navIcons!!.selected)
            } else {
                painterResource(id = navEntry.navIcons!!.unselected)
            }

            BottomNavigationItem(selected = isDestinationSelected, onClick = {
                navController.navigate(navEntry.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }, icon = {
                Icon(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(24.dp),
                    painter = icon,
                    contentDescription = null,
                    tint = selectedEntryColor(isDestinationSelected)
                )
            }, label = {
                Text(
                    text = navEntry.label, style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = if (isDestinationSelected) FontWeight(500) else FontWeight(400),
                        color = selectedEntryColor(isDestinationSelected),
                        textAlign = TextAlign.Center,
                    )
                )
            })
        }

    }
}

@Composable
private fun selectedEntryColor(selected: Boolean): Color {
    return if (selected) MaterialTheme.colorScheme.primary else Color(0xFF999999)
}