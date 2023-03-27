package dev.giridharaspk.madassignments.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResultItem(
    val login: String,
    val avatar_url: String,
    val url: String
)