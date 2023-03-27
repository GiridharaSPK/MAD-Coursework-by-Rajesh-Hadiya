package dev.giridharaspk.roomandnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.giridharaspk.roomandnavigation.datastore.DataStoreManager
import dev.giridharaspk.roomandnavigation.datastore.dataStore
import dev.giridharaspk.roomandnavigation.room.User
import dev.giridharaspk.roomandnavigation.ui.navigation.MyNavigation
import dev.giridharaspk.roomandnavigation.ui.theme.RoomAndNavigationTheme
import dev.giridharaspk.roomandnavigation.viewmodel.DbViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            RoomAndNavigationTheme {
                val navController = rememberNavController()
                val dbViewModel = hiltViewModel<DbViewModel>()
                Scaffold { innerPadding ->
                    MyNavigation(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        dbViewModel = dbViewModel
                    )
                }
            }
        }
    }
}


@Composable
fun UserDataField(key: String, value: String, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Text(
            text = "$key:", color = Color.DarkGray,
            style = MaterialTheme.typography.bodySmall,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Default
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(name: String) {
    TopAppBar(
        title = {
            Text(
                name,
                color = Color.DarkGray,
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = FontFamily.Default
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(2.dp)
    )
}

@Composable
fun UserDetailCard(
    user: User,
    item_index: Int,
    clickListener: (Int) -> Unit
) {
    var borderColor = Color.Cyan
    var backgroundColor = Color.White
    if (item_index % 2 == 0) {
        borderColor = Color.Green
        backgroundColor = Color(0xFFE7E7E7) //Color.LightGray
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
//                mutableList.removeAt(item_index)
                clickListener(item_index)
            }
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                .background(backgroundColor, RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UserDataField(
                    key = "USER ID",
                    value = user.userId.toString()
                )
                UserDataField(
                    key = "USERNAME",
                    value = user.username
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    UserDataField(
                        key = "FULL NAME",
                        value = user.fullName
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    UserDataField(
                        key = "EMAIL",
                        value = user.email
                    )
                }
                /*  Canvas(
                  modifier = Modifier
                      .size(size = 30.dp)
              ) {
                  drawCircle(
                      color = Color.Green
                  )
              }*/
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Green)
                        .background(Color(194, 255, 133, 255))
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        "${item_index.plus(1)}",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RoomAndNavigationTheme {
        HomeScreen(modifier = Modifier)
//        UsersListScreen(modifier = Modifier)
        val user = User(11, "username", "fullname", "email")
//        UserDetailScreen(modifier = Modifier, user)
    }
}*/
