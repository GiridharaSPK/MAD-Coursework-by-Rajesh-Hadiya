package dev.giridharaspk.retrofitandpaging.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubUser(
    val login: String,
    val avatar_url: String,
    val url: String,
    val public_repos: String,
    val followers: String,
    val following: String,
    val created_at: String,
    val name: String?,
    val bio: String?,
)
