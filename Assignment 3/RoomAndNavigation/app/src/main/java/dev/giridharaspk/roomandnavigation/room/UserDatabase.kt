package dev.giridharaspk.roomandnavigation.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = true,
    entities = [User::class]
)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}