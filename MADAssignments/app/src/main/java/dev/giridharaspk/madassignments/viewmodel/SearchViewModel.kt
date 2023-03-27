package dev.giridharaspk.madassignments.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.giridharaspk.madassignments.data.model.GithubUser
import dev.giridharaspk.madassignments.data.model.SearchResultItem
import dev.giridharaspk.madassignments.data.repository.GithubRepository
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
    /*
       private var _users = MutableStateFlow<MutableList<SearchResultItem>?>(null)
          val users: StateFlow<MutableList<SearchResultItem>?> = _users.asStateFlow()

       fun searchUser(searchText: String) = viewModelScope.launch(Dispatchers.IO) {
            _users.value = githubRepository.search(searchText, 1, 5)?.items?.toMutableList()
        }*/

    private val _selectedUser = MutableStateFlow<GithubUser?>(null)
    val selectedUser: StateFlow<GithubUser?> = _selectedUser.asStateFlow()

    fun searchUser(searchText: String): Flow<PagingData<SearchResultItem>>? {
        if(searchText.isBlank()) return null
        return githubRepository
            .getGitHubUsers(searchText = searchText)
            .cachedIn(viewModelScope)
    }

    fun getUserDetail(login: String) = viewModelScope.launch(Dispatchers.IO) {
        _selectedUser.value = githubRepository.userDetails(login)
    }

}