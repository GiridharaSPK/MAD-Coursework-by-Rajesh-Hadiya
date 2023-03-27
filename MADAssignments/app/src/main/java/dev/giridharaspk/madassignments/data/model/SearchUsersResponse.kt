package dev.giridharaspk.madassignments.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchUsersResponse(
    val incomplete_results: Boolean,
    val items: List<SearchResultItem>,
    val total_count: Int
)