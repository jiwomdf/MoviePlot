package com.katilijiwo.movieplot.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.katilijiwo.movieplot.data.remote.json.reviewmoviejson.Result
import com.katilijiwo.movieplot.util.Constant.API_PER_PAGE
import com.katilijiwo.movieplot.util.Constant.API_STARTING_PAGE

class CommentPagingSource(
    private val repository: Repository,
    private val movieID: Int
): PagingSource<Int, Result>() {

    private val RESPONSE_ITEMS_NULL_MSG = "response items is null"

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val startingPage = API_STARTING_PAGE
        val position = params.key ?: startingPage

        return try {
            val response = repository.fetchMovieReview(movieID, position)
            if(response.results != null){
                LoadResult.Page(
                    data = response.results,
                    prevKey = if(position == startingPage) null else position - 1,
                    nextKey = if(response.results.isEmpty()) null else position + 1
                )
            } else {
                LoadResult.Error(NullPointerException(RESPONSE_ITEMS_NULL_MSG))
            }
        } catch (ex: Exception){
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }
}