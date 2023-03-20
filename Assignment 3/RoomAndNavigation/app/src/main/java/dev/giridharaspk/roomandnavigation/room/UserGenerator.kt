package dev.giridharaspk.roomandnavigation.room

import kotlin.random.Random

fun getRandomUsers(n : Int): List<User> {
    val random = Random.Default
    return generateSequence {
        User(
            userId = random.nextLong(10000001, 88888888),
            username = generateRandomUsername(random),
            fullName = generateRandomName(random),
            email = generateRandomEmail(random)
        )
    }.take(n).toList()
}

fun generateRandomUsername(random: Random): String {
    return buildString {
        // Repeat the same caption randomly about 1-5 times.
//            val randomValue = Random.nextInt(1, 5)
        val chars = "abcdefghijklmnopqrstuvwxyz"
        append(chars.random())
        repeat(5) {
            if (random.nextBoolean())
                append(random.nextInt(0, 10))
            else {
                append(chars.random())
            }
        }
    }
}

fun generateRandomName(random: Random): String {
    val chars = "abcdefghijklmnopqrstuvwxyz "
    val userNameLength = random.nextInt(5, 21)

    var userName = ""
    repeat(userNameLength) {
        val randomIndex = (chars.indices).random()
        userName += chars[randomIndex]
    }
    return userName.trim().replaceFirstChar {
        it.uppercaseChar()
    }
}

fun generateRandomEmail(random: Random): String {
    val chars = "abcdefghijklmnopqrstuvwxyz0123456789"
    val emailLength = random.nextInt(5, 15)

    var email = ""
    repeat(emailLength) {
        val randomIndex = (chars.indices).random()
        email += chars[randomIndex]
    }
    val domainLength = random.nextInt(1, 5)

    var domain = ""
    if (random.nextBoolean()) {
        repeat(domainLength) {
            val randomIndex = (0 until 26).random()
            domain += chars[randomIndex]
        }
    } else {
        val arr = arrayOf("gmail", "yaaho", "outlook", "hotmail", "apple")
        domain = arr[random.nextInt(0, arr.size)]
    }

    val arr = arrayOf(".com", ".in", ".fr", ".us", ".uk", ".de", ".net", ".edu")
    return "$email@$domain${arr[random.nextInt(0, arr.size)]}"
}
