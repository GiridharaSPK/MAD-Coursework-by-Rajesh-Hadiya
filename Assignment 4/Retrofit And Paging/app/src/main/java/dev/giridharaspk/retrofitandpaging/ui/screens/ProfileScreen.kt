package dev.giridharaspk.retrofitandpaging.ui.screens

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dev.giridharaspk.retrofitandpaging.NamedTopBarWithNaviIcon
import dev.giridharaspk.retrofitandpaging.utils.DateTimeFormatter
import dev.giridharaspk.retrofitandpaging.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier,
    login: String,
    navController: NavController,
    viewModel: SearchViewModel
) {
    SideEffect {
        viewModel.getUserDetail(login)
    }
    val userDetails by remember { viewModel.selectedUser }.collectAsState()

    //todo update for horizontal layout
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            NamedTopBarWithNaviIcon("User Details") {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, "Back")
                }
            }
        },
        content = { paddingValues ->
            userDetails?.let { userDetails ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = userDetails.avatar_url,
                        contentDescription = "${userDetails.login}'s Image",
                        modifier = Modifier.clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    userDetails.name?.let {
                        Text(
                            it,
                            fontSize = 24.sp,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        userDetails.login,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(40.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .heightIn(min = 10.dp, max = 60.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                userDetails.followers,
                                fontSize = 24.sp,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                "Followers",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(2.dp),
                            color = Color.Black
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                userDetails.public_repos,
                                fontSize = 24.sp,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                "Repositories",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(2.dp),
                            color = Color.Black
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                userDetails.following ?: "",
                                fontSize = 24.sp,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                "Following",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    userDetails.bio?.let {
                        Text(
                            text = "Bio : $it",
                            fontSize = 18.sp,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    val time = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        DateTimeFormatter.convertTimeStampToReadableFormat(userDetails.created_at)
                    } else {
                        userDetails.created_at
                    }
                    Text(
                        text = "Github user since $time",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    )

}

@Preview(showSystemUi = true)
@Composable
fun DefaultPrev() {
    ProfileScreen(
        modifier = Modifier, login = "Giri",
        navController = rememberNavController(),
        viewModel = hiltViewModel<SearchViewModel>()
    )
}