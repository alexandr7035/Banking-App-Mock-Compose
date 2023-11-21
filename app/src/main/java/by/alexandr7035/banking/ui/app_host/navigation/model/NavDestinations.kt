package by.alexandr7035.banking.ui.app_host.navigation.model

import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.core.resources.UiText

sealed class NavDestinations {
    object Onboarding: NavDestinations()
    object Login: NavDestinations()
    object TermsAndConditions: NavDestinations()

    object SignUpGraph : NavDestinations() {
        object InitSignUp : NavDestinations()
        object ConfirmSignUp : NavDestinations()
        object CompletedSignUp : NavDestinations()
    }

    object SetupAppLockGraph : NavDestinations() {
        object CreatePin : NavDestinations()
        object EnableBiometrics : NavDestinations()
    }

    object RootGraph: NavDestinations() {
        object Home : NavDestinations()
        object TransactionHistory : NavDestinations()
        object Profile : NavDestinations()

        object AccountTopUp: NavDestinations()
        object AccountSend: NavDestinations()

        object CardList : NavDestinations()
        object AddCard: NavDestinations()
        object CardDetails: NavDestinations()

        object SavingsList: NavDestinations()
        object SavingDetails: NavDestinations()

        object Help: NavDestinations()

        object AppSettings: NavDestinations()
    }

    val route: String
        get() = this::class.java.simpleName

    companion object {
        fun primaryDestinations(): List<PrimaryDestination> {
            return listOf(
                PrimaryDestination(
                    route = RootGraph.Home.route,
                    navIcons = NavIconPair(
                        unselected = R.drawable.ic_home,
                        selected = R.drawable.ic_home_selected
                    ),
                    label = UiText.StringResource(R.string.home)
                ),
                PrimaryDestination(
                    route = RootGraph.TransactionHistory.route,
                    navIcons = NavIconPair(
                        unselected = R.drawable.ic_history,
                        selected = R.drawable.ic_history_selected
                    ),
                    label = UiText.StringResource(R.string.history)
                ),
                PrimaryDestination(
                    route = RootGraph.Profile.route,
                    navIcons = NavIconPair(
                        unselected = R.drawable.ic_profile,
                        selected = R.drawable.ic_profile_filled
                    ),
                    label = UiText.StringResource(R.string.profile)
                ),
            )
        }

        fun primaryDestinationsRoutes() = primaryDestinations()
            .map { it.route }
    }
}

data class PrimaryDestination(
    val route: String,
    val navIcons: NavIconPair,
    val label: UiText,
)