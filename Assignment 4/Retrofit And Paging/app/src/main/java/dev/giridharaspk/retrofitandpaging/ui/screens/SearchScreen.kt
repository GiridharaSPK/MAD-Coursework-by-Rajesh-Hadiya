package dev.giridharaspk.retrofitandpaging.ui.screens

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import dev.giridharaspk.retrofitandpaging.NamedTopBar
import dev.giridharaspk.retrofitandpaging.R
import dev.giridharaspk.retrofitandpaging.data.model.SearchResultItem
import dev.giridharaspk.retrofitandpaging.ui.navigation.TopLevelDestination
import dev.giridharaspk.retrofitandpaging.ui.theme.RetrofitAndPagingTheme
import dev.giridharaspk.retrofitandpaging.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: SearchViewModel,
    onSearchButtonClicked: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    val users: LazyPagingItems<SearchResultItem> =
        remember { viewModel.users }.collectAsLazyPagingItems()

    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        NamedTopBar("Github Users")
    }, content = { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(searchText, onSearchBarTextChange = { text ->
                searchText = text
            }, onSearchButtonClicked = {
                onSearchButtonClicked(searchText)
            })
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onSearchButtonClicked(searchText) },
                modifier = Modifier.wrapContentWidth()
            ) {
                Text("Search Users", color = Color.White)
            }
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
//                    .weight(1f)
//                    .padding()
                ,
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
/*
            If you are observing the loadState of a PagingData object using
            the collect method of a Flow or LiveData, you may initially
            receive a LoadState.Loading state, even if the PagingData object
            is empty. This can happen if the PagingSource that is supplying
            data to the PagingData object needs to load data from a remote
            data source, for example. In this case, the loadState will change
            to LoadState.NotLoading or LoadState.Error once the data has
            been loaded or if an error occurs.
*/
           /* users.apply {
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
                        // no-op
                    }

                    loadState.append is LoadState.Error -> {
                        RetryItem(
                            modifier = Modifier.fillMaxSize(),
                            onRetryClick = { retry() }
                        )
                    }
                }
            }*/
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
    @Composable
    fun Foo(){
        Text(text = "1", modifier = Modifier.widthIn(max = 32.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchBarTextChange: (String) -> Unit,
    onSearchButtonClicked: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = searchText,
        onValueChange = {
            onSearchBarTextChange(it)
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
            keyboardController?.hide()
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
    RetrofitAndPagingTheme {
        SearchScreen(
            Modifier, rememberNavController(), hiltViewModel()
        ) {

        }
    }
}
