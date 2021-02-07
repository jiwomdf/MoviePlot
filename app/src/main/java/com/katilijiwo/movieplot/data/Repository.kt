package com.katilijiwo.movieplot.data

import androidx.lifecycle.LiveData
import com.katilijiwo.movieplot.data.local.entity.Movie
import com.katilijiwo.movieplot.data.remote.json.moviedetail.MovieDetailResponse
import com.katilijiwo.movieplot.data.remote.json.popularmoviejson.PopularMovieReponse
import com.katilijiwo.movieplot.data.remote.json.reviewmoviejson.MovieReviewResponse
import com.katilijiwo.movieplot.data.remote.json.upcomingmoviejson.UpcomingMovieResponse

interface Repository {
    suspend fun fetPopularMovies(page: Int) : PopularMovieReponse
    suspend fun fetchUpcomingMovie(page: Int): UpcomingMovieResponse
    suspend fun fetchMovieDetail(movieID: Int): MovieDetailResponse
    suspend fun fetchMovieReview(movieID: Int, page: Int): MovieReviewResponse
    fun getAllMovieByDate(): LiveData<List<Movie>>
    fun getMovieByID(id: Int): LiveData<Movie?>
    suspend fun insertMovie(movie: Movie)
    suspend fun deleteMovie(movieID: Int)
}