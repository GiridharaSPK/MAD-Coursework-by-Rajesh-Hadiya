package dev.giridharaspk.roomandnavigation.room

import android.util.Log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbRepository @Inject constructor(
    private val userDao: UserDao,
) {
    val TAG = "DbRepository"

    suspend fun insertUsers(users: List<User>) {
//        userDao.insertUsers(*users.toTypedArray())
        userDao.insertUsers(users)
        Log.e(TAG, "inserted ${users.size} users")
        Log.e(TAG, "total users in DB : ${getUsers().size} users")
//        usersViewModel.saveUsersExistance(true)
    }

    suspend fun getUsers(): List<User> {
        val usersList = userDao.getUsers()
//        usersViewModel.saveUsersExistance(usersList.isNotEmpty())
        return usersList
    }

    suspend fun getUserWithId(userId : Long): User? {
        return userDao.getUserFromId(userId)
    }

    suspend fun getObservableUsers(): Flow<List<User>> {
        return userDao.getObservableUsers()
    }

    suspend fun deleteAll(users: List<User>) {
        userDao.deleteUsers(users = users)
//        usersViewModel.saveUsersExistance(false)
    }
}