package by.alexandr7035.banking.ui.app_host.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.compose.currentBackStackEntryAsState
import by.alexandr7035.banking.ui.app_host.navigation.model.NavDestinations
import by.alexandr7035.banking.ui.components.bottomnav.M2BottomNavigation
import by.alexandr7035.banking.ui.components.bottomnav.M2BottomNavigationItem
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun AppBottomNav(navController: NavController) {

    M2BottomNavigation(
        modifier = Modifier
            .height(
                72.dp
            )
            .shadow(elevation = 24.dp, spotColor = Color(0x08000000), ambientColor = Color(0x08000000)),
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        NavDestinations.primaryDestinations().forEach { destination ->
            val isDestinationSelected = currentDestination?.hierarchy?.any { it.route == destination.route } == true

            val icon = if (isDestinationSelected) {
                painterResource(id = destination.navIcons.selected)
            } else {
                painterResource(id = destination.navIcons.unselected)
            }

            M2BottomNavigationItem(selected = isDestinationSelected, onClick = {
                navController.navigate(destination.route) {
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
                        .size(24.dp)
                        ,
                    painter = icon,
                    contentDescription = null,
                    tint = selectedEntryColor(isDestinationSelected)
                )
            }, label = {
                Text(
                    text = destination.label.asString(), style = TextStyle(
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