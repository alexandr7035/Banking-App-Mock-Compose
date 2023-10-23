package by.alexandr7035.banking.ui.app_host.navigation.model

import by.alexandr7035.banking.R

sealed class NavEntries(
    val route: String,
    val navIcons: NavIconPair?,
    // TODO make label optional
    val label: String,
) {

    object Wizard : NavEntries(
        route = "wizard",
        navIcons = null,
        label = "wizard",
    )

    object Login : NavEntries(
        route = "login",
        navIcons = null,
        label = "Login",
    )

    object InitSignUp: NavEntries(
        route = "signup_init",
        navIcons = null,
        label = "Sign Up",
    )

    object ConfirmSignUp: NavEntries(
        route = "signup_confirm",
        navIcons = null,
        label = "Sign Up confirm",
    )

    object Profile : NavEntries(
        route = "profile",
        navIcons = NavIconPair(
            unselected = R.drawable.ic_profile,
            selected = R.drawable.ic_profile_filled
        ),
        label = "Profile",
    )

    object Statistics : NavEntries(
        route = "statistics",
        navIcons = NavIconPair(
            unselected = R.drawable.ic_statistics,
            selected = R.drawable.ic_statistics_selected
        ),
        label = "Statistics",
    )

    object Home : NavEntries(
        route = "home",
        navIcons = NavIconPair(
            unselected = R.drawable.ic_home,
            selected = R.drawable.ic_home_selected
        ),
        label = "Home",
    )

    object History : NavEntries(
        route = "history",
        navIcons = NavIconPair(
            unselected = R.drawable.ic_history,
            selected = R.drawable.ic_history_selected
        ),
        label = "History",
    )

    object CardList : NavEntries(
        route = "card_list",
        navIcons = null,
        label = "Card List"
    )

    object AddCard: NavEntries(
        route = "add_card",
        navIcons = null,
        label = "Add Card"
    )

    object CardDetails: NavEntries(
        route = "card_details",
        navIcons = null,
        label = "Card Details"
    )

    object SavingsList: NavEntries(
        route = "savings_list",
        navIcons = null,
        label = "Savings List"
    )

    object SavingDetails: NavEntries(
        route = "saving_details",
        navIcons = null,
        label = "Saving Details"
    )

    sealed class Graphs(val route: String) {
        object HomeGraph : Graphs("homeGraph")
        object SignUpGraph : Graphs("signUpGraph")
    }

    companion object {
        val primaryDestinations = listOf(
            Home.route,
            History.route,
            Statistics.route,
            Profile.route,
        )
    }
}
