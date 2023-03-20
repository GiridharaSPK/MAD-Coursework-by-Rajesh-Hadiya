package dev.giridharaspk.roomandnavigation.ui.screens

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.giridharaspk.roomandnavigation.MyTopBar
import dev.giridharaspk.roomandnavigation.UserDetailCard
import dev.giridharaspk.roomandnavigation.viewmodel.DbViewModel
import dev.giridharaspk.roomandnavigation.room.getRandomUsers
import dev.giridharaspk.roomandnavigation.ui.navigation.TopLevelDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    modifier: Modifier,
    navController: NavController,
    dbViewModel: DbViewModel
) {
    val context = LocalContext.current
    val users by remember {
        dbViewModel.users
    }.collectAsState(null)
//        remember { getRandomUsers(5).toMutableStateList() } //todo remove and retrieve from room
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
//        dbViewModel.getUsers()
        dbViewModel.getObservableUsers()
    }

    Scaffold(
        topBar = { MyTopBar("User Directory") },
        content = { padding ->
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(padding),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        count = users?.size ?: 0,
                        key = { it },
                    ) { index ->
                        users?.get(index)?.let { user ->
                            UserDetailCard(user, index) {
                                navController.navigate(
                                    "${TopLevelDestination.UserDetailScreen.route}/${user.userId}"
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        val randomSingleUserList = getRandomUsers(1)
                        val newUser = randomSingleUserList[0]
//                        users?.add(newUser)
                        scope.launch {
                            dbViewModel.insertUsers(randomSingleUserList)
                        }
                        val newUserFullName = getStyledNewUserFullName(newUser.fullName)

                        Toast.makeText(
                            context,
                            newUserFullName,  //  "User ${newUser.fullName.uppercase()} added",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(12.dp)
                ) {
                    Text("Add a User")
                }
            }
        }
    )
}

fun getStyledNewUserFullName(fullName: String): SpannableStringBuilder {
    val spannableStringBuilder = SpannableStringBuilder("User added")
    spannableStringBuilder.setSpan(
        ForegroundColorSpan(Color.Red.toArgb()),
        4, // start
        5, // end
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    spannableStringBuilder.insert(4, " $fullName")
    return spannableStringBuilder

}


/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RoomAndNavigationTheme {
        val user = User(11, "username", "fullname", "email")
        UsersListScreen(modifier = Modifier)
    }
}*/
