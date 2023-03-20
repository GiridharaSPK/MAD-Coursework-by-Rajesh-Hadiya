package dev.giridharaspk.roomandnavigation.ui.navigation

sealed class TopLevelDestination(val title: String, val route: String) {
    object HomeScreen : TopLevelDestination(title = "Home", route = "home")
    object UsersListScreen : TopLevelDestination(title = "Users List", route = "users-list")
    object UserDetailScreen : TopLevelDestination(title = "User Detail", route = "user-detail")
}