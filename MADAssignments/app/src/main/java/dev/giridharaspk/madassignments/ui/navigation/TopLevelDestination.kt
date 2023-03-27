package dev.giridharaspk.madassignments.ui.navigation

sealed class TopLevelDestination(val title: String, val route: String) {
    object SearchScreen : TopLevelDestination(title = "Search", route = "search")
    object UserDetailScreen : TopLevelDestination(title = "User Detail", route = "user-detail")
}