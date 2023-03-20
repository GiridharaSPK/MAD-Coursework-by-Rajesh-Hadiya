package dev.giridharaspk.roomandnavigation.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


val Context.dataStore by preferencesDataStore(name = "my_datastore")

class DataStoreManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val USERS_EXIST = booleanPreferencesKey(name = "users_exist")

    suspend fun saveUsersExist(authenticated: Boolean) {
        context.dataStore.edit { preference ->
            preference[USERS_EXIST] = authenticated
        }
    }

    val usersExist = context.dataStore.data.map { preference ->
        preference[USERS_EXIST] ?: false
    }
}