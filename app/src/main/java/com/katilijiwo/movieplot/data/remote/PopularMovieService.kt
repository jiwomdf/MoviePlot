package com.katilijiwo.movieplot.data.remote

import com.katilijiwo.movieplot.base.BaseService
import com.katilijiwo.movieplot.data.remote.json.moviedetail.MovieDetailResponse
import com.katilijiwo.movieplot.data.remote.json.popularmoviejson.PopularMovieReponse
import com.katilijiwo.movieplot.data.remote.json.reviewmoviejson.MovieReviewResponse
import com.katilijiwo.movieplot.data.remote.json.upcomingmoviejson.UpcomingMovieResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class PopularMovieService(private val api: Api) : BaseService() {

    fun fetchPopularMovies(page: Int) : Deferred<PopularMovieReponse> {
        return CoroutineScope(Dispatchers.IO).async {
            execute(api.getPopularMovies(page))
        }
    }

    fun fetchUpcomingMovie(page: Int): Deferred<UpcomingMovieResponse>{
        return CoroutineScope(Dispatchers.IO).async {
            execute(api.fetchUpcomingMovies(page))
        }
    }

    fun fetchMovieDetail(movieID: Int): Deferred<MovieDetailResponse>{
        return CoroutineScope(Dispatchers.IO).async {
            execute(api.fetchMovieDetail(movieID))
        }
    }

    fun fetchMovieReview(movieID: Int, page: Int): Deferred<MovieReviewResponse> {
        return CoroutineScope(Dispatchers.IO).async {
            execute(api.fetchMovieReview(movieID, page))
        }
    }
}