package dev.giridharaspk.madassignments.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.giridharaspk.madassignments.data.model.GithubUser
import dev.giridharaspk.madassignments.data.model.SearchResultItem
import dev.giridharaspk.madassignments.data.model.SearchUsersResponse
import dev.giridharaspk.madassignments.data.network.GithubApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val githubApi: GithubApi
) {

    /* suspend fun search(searchText: String, page: Int, size: Int): SearchUsersResponse? {
         val response = githubApi.getGitHubUsers(searchText, page, size)
         if (!response.isSuccessful) return null
         return response.body()
     }*/

    fun getGitHubUsers(searchText: String): Flow<PagingData<SearchResultItem>> {
        Log.d("log", "getGitHubUsers($searchText)")

        return Pager(config = PagingConfig(pageSize = 5)) {
            GithubSearchUsersRepository(
                githubApi = githubApi,
                searchText = searchText
            )
        }.flow
    }


    suspend fun userDetails(login: String): GithubUser? {
        val response = githubApi.getUserDetails(login)
        if (!response.isSuccessful) return null
        return response.body()
    }

}