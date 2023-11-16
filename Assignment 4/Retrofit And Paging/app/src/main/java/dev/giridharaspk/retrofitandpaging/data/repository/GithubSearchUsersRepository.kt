package dev.giridharaspk.retrofitandpaging.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.giridharaspk.retrofitandpaging.data.model.SearchResultItem
import dev.giridharaspk.retrofitandpaging.data.model.SearchUsersResponse
import dev.giridharaspk.retrofitandpaging.data.network.GithubApi

const val FIRST_PAGE = 1

class GithubSearchUsersRepository(
    private val githubApi: GithubApi, private val searchText: String
) : PagingSource<Int, SearchResultItem>() {

    override fun getRefreshKey(state: PagingState<Int, SearchResultItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultItem> {
        Log.d("log", "load()")
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = githubApi.getGitHubUsers(
                searchText = searchText,
                page = page,
                size = params.loadSize
            )
            LoadResult.Page(
                data = response.items, //response.body()?.items!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.items.isNullOrEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}