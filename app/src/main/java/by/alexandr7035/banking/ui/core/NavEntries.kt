package by.alexandr7035.banking.ui.core

import by.alexandr7035.banking.R

sealed class NavEntries(
    val route: String,
    val navIcons: NavIconPair?,
    val label: String,
) {

    object Wizard: NavEntries(
        route = "wizard",
        navIcons = null,
        label = "wizard",
    )

    object Login: NavEntries(
        route = "login",
        navIcons = null,
        label = "Login",
    )


    object Profile: NavEntries(
        route = "profile",
        navIcons = NavIconPair(
            unselected = R.drawable.ic_profile,
            selected = R.drawable.ic_profile_filled
        ),
        label = "Profile",
    )

    object Statistics: NavEntries(
        route = "statistics",
        navIcons = NavIconPair(
            unselected = R.drawable.ic_statistics,
            selected = R.drawable.ic_statistics_selected
        ),
        label = "Statistics",
    )

    object Home: NavEntries(
        route = "home",
        navIcons = NavIconPair(
            unselected = R.drawable.ic_home,
            selected = R.drawable.ic_home_selected
        ),
        label = "Home",
    )

    object History: NavEntries(
        route = "history",
        navIcons = NavIconPair(
            unselected = R.drawable.ic_history,
            selected = R.drawable.ic_history_selected
        ),
        label = "History",
    )

    object LogoutDialog: NavEntries(
        route = "logout_dialog",
        navIcons = null,
        label = "Log out"
    )

    sealed class Graphs(val route: String) {
        object HomeGraph: Graphs("homeGraph")
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
