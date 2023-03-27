package dev.giridharaspk.madassignments.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.giridharaspk.madassignments.NamedTopBar
import dev.giridharaspk.madassignments.R
import dev.giridharaspk.madassignments.data.model.SearchResultItem
import dev.giridharaspk.madassignments.ui.navigation.TopLevelDestination
import dev.giridharaspk.madassignments.ui.theme.MADAssignmentsTheme
import dev.giridharaspk.madassignments.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: SearchViewModel,
    onSearchButtonClicked: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    val users: LazyPagingItems<SearchResultItem>? =
        remember { viewModel.searchUser("") }?.collectAsLazyPagingItems()

    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        NamedTopBar("Github Users")
    }, content = { padding ->
        var searchTextVisible = false
        val showSearchText by remember { mutableStateOf(searchTextVisible) }
        LaunchedEffect(key1 = users) {
            searchTextVisible = (users?.itemCount ?: 0) > 0
        }
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchBar(searchText, onSearch = {
                searchText = it
//                searchTextVisible = false
            }, onSearchButtonClicked = { onSearchButtonClicked(searchText) })
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onSearchButtonClicked(searchText) },
                modifier = Modifier.wrapContentWidth()
            ) {
                Text("Search Users", color = Color.White)
            }
            if (showSearchText) {
                Text("Search results of $searchText")
            }

            users?.let {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(padding),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(users) { user ->
                        user?.let {
                            GithubProfileItem(user) {
                                navController.navigate(
                                    "${TopLevelDestination.UserDetailScreen.route}/${user.login}"
                                )
                            }
                        }
                    }
                }
                /* items(
                 count = users?.size ?: 0,
                 key = { it },
             ) { index ->
                 users?.get(index)?.let { user ->
                     GithubProfileItem(user) { login ->
                         navController.navigate(
                             "${TopLevelDestination.UserDetailScreen.route}/${user.login}"
                         )
                     }
                 }
             }*/
                users.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            LoadingProgressBar(modifier = Modifier.fillMaxSize())
                        }

                        loadState.append is LoadState.Loading -> {
                            LoadingProgressBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }

                        loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && this.itemCount < 1 -> {
                            // Show no content UI here
                        }

                        loadState.refresh is LoadState.Error -> {
                            /* no-op */
                        }

                        loadState.append is LoadState.Error -> {
                            RetryItem(
                                modifier = Modifier.fillMaxSize(),
                                onRetryClick = { retry() }
                            )
                        }
                    }
                }
            }

        }
    })
}

@Composable
fun LoadingProgressBar(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 4.dp
) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .align(Alignment.Center),
            color = color,
            strokeWidth = strokeWidth
        )
    }
}


@Composable
fun RetryItem(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(onClick = onRetryClick) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String, onSearch: (String) -> Unit, onSearchButtonClicked: () -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = {
            onSearch(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        placeholder = { Text("Search Github User") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.Gray
            )
        },
        keyboardActions = KeyboardActions(onSearch = {
            onSearchButtonClicked()
        }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent
        )
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MADAssignmentsTheme() {
        SearchScreen(
            Modifier, rememberNavController(), hiltViewModel()
        ) {

        }
    }
}
