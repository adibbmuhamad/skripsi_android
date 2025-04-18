import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Beranda", "main_page", Icons.Outlined.Home),
        BottomNavItem("Siswa", "student_list", Icons.Outlined.School),
        BottomNavItem("Notifikasi", "notification_page", Icons.Outlined.Notifications),
        BottomNavItem("Saya", "profile_page", Icons.Outlined.Person)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Check if the current route is one of the main pages
    val shouldShowBottomBar = currentRoute in items.map { it.route }

    if (shouldShowBottomBar) {
        BottomNavigation(
            modifier = Modifier
                .navigationBarsPadding() // Padding untuk navigasi sistem
                .padding(bottom = 16.dp), // Padding tambahan
            backgroundColor = Color(0xFFF9FAFB),
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                val color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFF909096)
                BottomNavigationItem(
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = item.title,
                            tint = color
                        )
                    },
                    label = {
                        Text(
                            item.title,
                            color = color,
                            style = MaterialTheme.typography.bodyMedium.copy(),
                            maxLines = 1
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            // Avoid multiple copies of the same destination
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                            // Pop up to the start destination of the graph to avoid building up a large stack of destinations
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                )
            }
        }
    }
}

data class BottomNavItem(val title: String, val route: String, val icon: ImageVector)