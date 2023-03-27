package dev.giridharaspk.madassignments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.giridharaspk.madassignments.ui.navigation.SearchNavigation
import dev.giridharaspk.madassignments.ui.theme.MADAssignmentsTheme
import dev.giridharaspk.madassignments.viewmodel.SearchViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
//            val users = remember { viewModel.users }.collectAsState() //collectAsLazyPagingItems()

            MADAssignmentsTheme {
                val navController = rememberNavController()
                /* SearchScreen(
                     Modifier,
 //                    navController,
                     viewModel,
                     onSearchButtonClicked = { searchText ->
                         viewModel.searchUser(searchText)
                     }
                 )*/

                SearchNavigation(
                    modifier = Modifier,
                    navController = navController,
                    viewModel = viewModel
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NamedTopBar(name: String) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NamedTopBarWithNaviIcon(name: String, navigationIcon: @Composable () -> Unit) {
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
            .padding(2.dp),
        navigationIcon = navigationIcon
    )
}