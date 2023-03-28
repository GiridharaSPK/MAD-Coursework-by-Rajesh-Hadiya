package dev.giridharaspk.retrofitandpaging.data.network

import dev.giridharaspk.retrofitandpaging.data.model.GithubUser
import dev.giridharaspk.retrofitandpaging.data.model.SearchUsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("search/users")
    suspend fun getGitHubUsers(
        @Query("q") searchText: String,
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ): Response<SearchUsersResponse>

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): Response<GithubUser>
}