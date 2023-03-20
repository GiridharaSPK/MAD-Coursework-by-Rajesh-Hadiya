package dev.giridharaspk.roomandnavigation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.giridharaspk.roomandnavigation.MyTopBar
import dev.giridharaspk.roomandnavigation.UserDataField
import dev.giridharaspk.roomandnavigation.room.User
import dev.giridharaspk.roomandnavigation.ui.theme.RoomAndNavigationTheme
import dev.giridharaspk.roomandnavigation.viewmodel.DbViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    modifier: Modifier,
    userId: Long,
    navController: NavController,
    dbViewModel: DbViewModel
) {
    val user by remember { dbViewModel.selectedUser }.collectAsState()
    LaunchedEffect(key1 = Unit){
        dbViewModel.getUserWithUserId(userId)
    }

    Scaffold(
        topBar = { UserDetailTopBar(navController = navController) },
        content = { padding ->
            Box(
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .padding(padding)
            ) {
                /* Column(
                     modifier = Modifier.align(Alignment.TopCenter),
                     verticalArrangement = Arrangement.Center
                 ) {*/
                Text(
                    "Welcome to user detail screen",
                    modifier = Modifier
                        .padding(20.dp)
                        .align(Alignment.TopCenter)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.CenterStart)
                ) {
                    UserDataField(
                        key = "User ID",
                        value = user?.userId.toString(),
                        modifier = Modifier.padding(4.dp)
                    )
                    UserDataField(
                        key = "User Name",
                        value = user?.username ?: "",
                        modifier = Modifier.padding(4.dp)
                    )
                    UserDataField(
                        key = "Full Name",
                        value = user?.fullName ?: "",
                        modifier = Modifier.padding(4.dp)
                    )
                    UserDataField(
                        key = "Email ID",
                        value = user?.email ?: "",
                        modifier = Modifier.padding(4.dp)
                    )
//                }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                "User Directory",
                color = Color.DarkGray,
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = FontFamily.Default
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(2.dp)
    )
}

/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RoomAndNavigationTheme {
        val user = User(11, "username", "fullname", "email")
        UserDetailScreen(modifier = Modifier, user)
    }
}*/
