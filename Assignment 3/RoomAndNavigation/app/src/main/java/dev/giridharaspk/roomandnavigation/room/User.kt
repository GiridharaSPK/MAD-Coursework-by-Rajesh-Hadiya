package dev.giridharaspk.roomandnavigation.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId: Long,
    val username: String,
    val fullName: String,
    val email: String,
)
