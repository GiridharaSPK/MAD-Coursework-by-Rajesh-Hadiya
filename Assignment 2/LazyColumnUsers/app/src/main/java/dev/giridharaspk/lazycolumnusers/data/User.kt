package dev.giridharaspk.lazycolumnusers.data

import androidx.compose.ui.text.capitalize
import java.util.*
import kotlin.random.Random

data class User(
    val userId: Long,
    val username: String,
    val fullName: String,
    val email: String
)

val usersGenerator = generateSequence {

    User(
        userId = Random.nextLong(10000001, 88888888),
        username =
        buildString {
            // Repeat the same caption randomly about 1-5 times.
//            val randomValue = Random.nextInt(1, 5)
            val chars = "abcdefghijklmnopqrstuvwxyz"
            append(chars.random())
            repeat(5) {
                if (Random.nextBoolean())
                    append(Random.nextInt(0, 10))
                else {
                    append(chars.random())
                }
            }
        },
        fullName = generateRandomName(),
        email = generateRandomEmail()
    )
}

fun generateRandomName(): String {
    val chars = "abcdefghijklmnopqrstuvwxyz "
    val userNameLength = Random.nextInt(5, 21)

    var userName = ""
    repeat(userNameLength) {
        val randomIndex = (chars.indices).random()
        userName += chars[randomIndex]
    }
    return userName.trim().replaceFirstChar {
        it.uppercaseChar()
    }
}

fun generateRandomEmail(): String {
    val chars = "abcdefghijklmnopqrstuvwxyz0123456789"
    val emailLength = Random.nextInt(5, 15)

    var email = ""
    repeat(emailLength) {
        val randomIndex = (chars.indices).random()
        email += chars[randomIndex]
    }
    val domainLength = Random.nextInt(1, 5)

    var domain = ""
    if (Random.nextBoolean()) {
        repeat(domainLength) {
            val randomIndex = (0 until 26).random()
            domain += chars[randomIndex]
        }
    } else {
        val arr = arrayOf("gmail", "yaaho", "outlook", "hotmail", "apple")
        domain = arr[Random.nextInt(0, arr.size)]
    }

    val arr = arrayOf(".com", ".in", ".fr", ".us", ".uk", ".de", ".net", ".edu")
    return "$email@$domain${arr[Random.nextInt(0, arr.size)]}"
}

