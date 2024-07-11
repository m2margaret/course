import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mysaintp.ui.theme.CategoriesScreen
import com.example.mysaintp.ui.theme.PlacesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "categories"
    ) {
        composable(route ="categories") {
            CategoriesScreen(
                onItemClick = { category, placeType ->
                navController.navigate("places/${category.placeType}")
                }
            )
        }
        composable(
            route = "places/{type}",
            arguments = listOf(navArgument("type") { type = NavType.IntType })
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getInt("type")
            //Log.d("ФЛЩ", "ФДЩ")
            PlacesScreen(type = type, onBackPressed = {navController.navigate("categories")})
        }
    }
}