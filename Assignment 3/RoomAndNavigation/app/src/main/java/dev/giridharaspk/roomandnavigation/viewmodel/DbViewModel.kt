package dev.giridharaspk.roomandnavigation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.giridharaspk.roomandnavigation.datastore.DataStoreManager
import dev.giridharaspk.roomandnavigation.room.DbRepository
import dev.giridharaspk.roomandnavigation.room.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(
    private val dbRepository: DbRepository,
//    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _users = MutableStateFlow<MutableList<User>?>(null)
    val users: StateFlow<MutableList<User>?> = _users.asStateFlow()

    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> = _selectedUser.asStateFlow()
//    val usersExist = dataStoreManager.usersExist

//    private val _users = mutableStateOf<List<User>>(emptyList())
//    val users: State<List<User>> = _users

//    fun insertUsers(users: List<User>) = viewModelScope.launch {
//        dbRepository.insertUsers(users)
//    }

    suspend fun insertUsers(users: List<User>) = dbRepository.insertUsers(users)

    fun getUsers() = viewModelScope.launch {
        val users = dbRepository.getUsers().toMutableList()
        _users.value = users
    }

    fun getUserWithUserId(userId: Long) {
        viewModelScope.launch {
            _selectedUser.value = dbRepository.getUserWithId(userId)
        }
    }

//    suspend fun getObservableUsers(): Flow<List<User>> {
//        return dbRepository.getObservableUsers()
//    }

    suspend fun getObservableUsers() {
        dbRepository.getObservableUsers().collect { users ->
            _users.value = users.toMutableList()
        }
    }

    suspend fun deleteAll(users: List<User>) {
        dbRepository.deleteAll(users = users)
    }
}