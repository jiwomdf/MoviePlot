package com.katilijiwo.movieplot.data.remote

import com.katilijiwo.movieplot.base.BaseService
import com.katilijiwo.movieplot.data.remote.json.moviedetail.MovieDetailResponse
import com.katilijiwo.movieplot.data.remote.json.popularmoviejson.PopularMovieReponse
import com.katilijiwo.movieplot.data.remote.json.reviewmoviejson.MovieReviewResponse
import com.katilijiwo.movieplot.data.remote.json.upcomingmoviejson.UpcomingMovieResponse
import com.katilijiwo.movieplot.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class PopularMovieService(private val api: Api) : BaseService() {

    suspend fun fetchPopularMovies(page: Int) : PopularMovieReponse {
        return when(val result = createCall { api.getPopularMovies(page) }){
            is Resource.Success -> result.data
            is Resource.Error -> throw result.error
        }
    }

    suspend fun fetchUpcomingMovie(page: Int): UpcomingMovieResponse {
        return when(val result = createCall { api.fetchUpcomingMovies(page) }){
            is Resource.Success -> result.data
            is Resource.Error -> throw result.error
        }
    }

    suspend fun fetchMovieDetail(movieID: Int): MovieDetailResponse {
        return when(val result = createCall { api.fetchMovieDetail(movieID) }){
            is Resource.Success -> result.data
            is Resource.Error -> throw result.error
        }
    }

    suspend fun fetchMovieReview(movieID: Int, page: Int): MovieReviewResponse {
        return when(val result = createCall { api.fetchMovieReview(movieID, page) }){
            is Resource.Success -> result.data
            is Resource.Error -> throw result.error
        }
    }
}