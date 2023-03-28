package dev.giridharaspk.retrofitandpaging.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.giridharaspk.retrofitandpaging.ui.screens.ProfileScreen
import dev.giridharaspk.retrofitandpaging.ui.screens.SearchScreen
import dev.giridharaspk.retrofitandpaging.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun SearchNavigation(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: SearchViewModel
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TopLevelDestination.SearchScreen.route
    ) {
        composable(route = TopLevelDestination.SearchScreen.route) {

            SearchScreen(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel,
                onSearchButtonClicked = { searchText ->
                    viewModel.searchUser(searchText)
                }
            )
        }

        composable(
            route = TopLevelDestination.UserDetailScreen.route + "/{login}",
            arguments = listOf(
                navArgument("login") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val login = navBackStackEntry.arguments?.getString("login") ?: "giridharaspk"

            ProfileScreen(
                modifier = modifier,
                login = login,
                navController = navController,
                viewModel = viewModel
            )
        }
    }

}
