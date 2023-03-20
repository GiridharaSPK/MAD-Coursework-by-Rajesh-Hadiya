package dev.giridharaspk.roomandnavigation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.giridharaspk.roomandnavigation.MyTopBar
import dev.giridharaspk.roomandnavigation.viewmodel.DbViewModel
import dev.giridharaspk.roomandnavigation.room.getRandomUsers
import dev.giridharaspk.roomandnavigation.ui.navigation.TopLevelDestination
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    dbViewModel: DbViewModel = hiltViewModel(),
    navController: NavController
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { MyTopBar("User Directory") },
        content = { padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            dbViewModel.insertUsers(getRandomUsers(5))
                        }
                        navController.navigate(TopLevelDestination.UsersListScreen.route)
                    },
                    modifier = modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                ) {
                    Text("Add all Users")
                }
            }
        }
    )
}

/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RoomAndNavigationTheme {
        HomeScreen(modifier = Modifier)
    }
}*/
