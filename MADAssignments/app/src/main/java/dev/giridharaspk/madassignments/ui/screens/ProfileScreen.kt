package dev.giridharaspk.madassignments.ui.screens

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
import dev.giridharaspk.madassignments.NamedTopBar
import dev.giridharaspk.madassignments.NamedTopBarWithNaviIcon
import dev.giridharaspk.madassignments.viewmodel.SearchViewModel

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
//    LaunchedEffect(key1 = userDetails){
//        Log.d("log", "userDetails : $userDetails")
//    }
    //need not handle states because login is always valid
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
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = userDetails?.avatar_url,
                    contentDescription = "${userDetails?.login}'s Image",
                    modifier = Modifier.clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    userDetails?.name ?: "",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    userDetails?.login ?: "",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            userDetails?.followers ?: "",
                            fontSize = 24.sp,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            "Followers",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }

                    Divider(modifier = Modifier, thickness = 4.dp, color = Color.Black)

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            userDetails?.public_repos ?: "",
                            fontSize = 24.sp,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            "Repositories",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }

                    Divider(modifier = Modifier, thickness = 4.dp, color = Color.Black)

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            userDetails?.following ?: "",
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

                Text(
                    text = "Bio : ${userDetails?.bio ?: ""}",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Created At : ${userDetails?.created_at ?: ""}",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleSmall
                )
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