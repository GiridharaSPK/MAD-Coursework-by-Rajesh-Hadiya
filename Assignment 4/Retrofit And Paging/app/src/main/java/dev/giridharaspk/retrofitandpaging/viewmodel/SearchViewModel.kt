package dev.giridharaspk.retrofitandpaging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.giridharaspk.retrofitandpaging.data.model.GithubUser
import dev.giridharaspk.retrofitandpaging.data.model.SearchResultItem
import dev.giridharaspk.retrofitandpaging.data.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val githubRepository: GithubRepository) :
    ViewModel() {

    private var _users = MutableStateFlow<PagingData<SearchResultItem>>(PagingData.empty())
    val users: MutableStateFlow<PagingData<SearchResultItem>> = _users

    private val _selectedUser = MutableStateFlow<GithubUser?>(null)
    val selectedUser: StateFlow<GithubUser?> = _selectedUser.asStateFlow()

    fun searchUser(searchText: String) = viewModelScope.launch(Dispatchers.IO) {
        githubRepository
            .searchGitHubUsers(searchText = searchText)
            .cachedIn(viewModelScope)
            .collect {
                //todo move collect to UI
                _users.value = it
            }
    }

    /* fun searchUser(searchText: String): Flow<PagingData<SearchResultItem>> {
         return githubRepository
             .searchGitHubUsers(searchText = searchText)
             .cachedIn(viewModelScope)
     }*/

    fun getUserDetail(login: String) = viewModelScope.launch(Dispatchers.IO) {
        _selectedUser.value = githubRepository.userDetails(login)
    }

}