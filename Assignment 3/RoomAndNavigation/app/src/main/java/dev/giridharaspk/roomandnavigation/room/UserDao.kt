package dev.giridharaspk.roomandnavigation.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Upsert
    suspend fun insertUsers(users: List<User>)
//    suspend fun insertUsers(vararg users: User)

    @Query("SELECT * FROM Users")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM Users WHERE userId = :userId")
    suspend fun getUserFromId(userId : Long): User?

    @Query("SELECT * FROM Users")
    fun getObservableUsers(): Flow<List<User>>

    @Delete
    suspend fun deleteUsers(users: List<User>)
}