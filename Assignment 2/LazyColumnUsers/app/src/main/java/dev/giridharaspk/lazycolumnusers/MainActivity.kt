package dev.giridharaspk.lazycolumnusers

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.giridharaspk.lazycolumnusers.data.User
import dev.giridharaspk.lazycolumnusers.data.usersGenerator
import dev.giridharaspk.lazycolumnusers.ui.theme.LazyColumnUsersTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnUsersTheme {
                UsersScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen() {
    Scaffold(
        topBar = { MyTopBar() },
        content = { padding ->
            UsersListLazyLayout(modifier = Modifier.padding(padding))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar() {
    TopAppBar(
        title = {
            Text(
                "Users".uppercase(),
                color = Color.DarkGray,
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = FontFamily.Monospace
            )
        },
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .background(Color.White)
            .border(2.dp, Color.DarkGray, RectangleShape)
    )
}

@Composable
fun UsersListLazyLayout(modifier: Modifier) {
    val context = LocalContext.current
    val mutableList =
        remember { mutableStateListOf(*usersGenerator.take(100).toList().toTypedArray()) }
//        remember { mutableStateListOf(usersGenerator.take(100)) }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = mutableList.size,
            key = { it },
        ) { item_index ->
            UserDetailCard(mutableList[item_index], item_index) { index ->
                val deletedUser = mutableList.removeAt(index)
                Toast.makeText(
                    context,
                    "User ${deletedUser.fullName.uppercase()} deleted",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
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
                        .size(30.dp)
                        .background(Color.Green, CircleShape)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        "${item_index.plus(1)}",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun UserDataField(key: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LazyColumnUsersTheme {
        UsersScreen()
    }
}