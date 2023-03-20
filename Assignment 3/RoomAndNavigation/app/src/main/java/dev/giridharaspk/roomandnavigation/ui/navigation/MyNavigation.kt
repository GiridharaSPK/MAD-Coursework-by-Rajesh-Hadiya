package dev.giridharaspk.roomandnavigation.ui.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.giridharaspk.roomandnavigation.datastore.DataStoreManager
import dev.giridharaspk.roomandnavigation.viewmodel.DbViewModel
import dev.giridharaspk.roomandnavigation.room.User
import dev.giridharaspk.roomandnavigation.ui.screens.HomeScreen
import dev.giridharaspk.roomandnavigation.ui.screens.UserDetailScreen
import dev.giridharaspk.roomandnavigation.ui.screens.UsersListScreen

@Composable
fun MyNavigation(
    modifier: Modifier,
    navController: NavHostController,
) {
    val dbViewModel: DbViewModel = hiltViewModel()

    val start = if (dbViewModel.users.collectAsState(initial = null).value?.isEmpty() == true) {
        TopLevelDestination.HomeScreen.route
    } else {
        TopLevelDestination.UsersListScreen.route
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = start //TopLevelDestination.HomeScreen.route
    ) {
        composable(route = TopLevelDestination.HomeScreen.route) {
//            private val dbViewModel by viewModels<DbViewModel>()
            HomeScreen(
                modifier = Modifier,
                navController = navController,
                dbViewModel = dbViewModel
            )
        }

        composable(route = TopLevelDestination.UsersListScreen.route) {

            if (start == TopLevelDestination.UsersListScreen.route) {
                BackHandler(enabled = true) {
                    //Do nothing
                }
            }

            UsersListScreen(
                modifier = Modifier,
                navController = navController,
                dbViewModel = dbViewModel
            )
        }

        composable(
            route = TopLevelDestination.UserDetailScreen.route + "/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.LongType
                }
            )
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getLong("userId") ?: 0

            UserDetailScreen(
                modifier = Modifier,
                userId = userId,
                navController = navController,
                dbViewModel = dbViewModel
            )
        }
    }

}
