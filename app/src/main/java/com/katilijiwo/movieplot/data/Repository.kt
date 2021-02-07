package com.katilijiwo.movieplot.data

import androidx.lifecycle.LiveData
import com.katilijiwo.movieplot.data.local.dao.MovieDao
import com.katilijiwo.movieplot.data.local.entity.Movie
import com.katilijiwo.movieplot.data.remote.PopularMovieService
import com.katilijiwo.movieplot.data.remote.json.moviedetail.MovieDetailResponse
import com.katilijiwo.movieplot.data.remote.json.popularmoviejson.PopularMovieReponse
import com.katilijiwo.movieplot.data.remote.json.reviewmoviejson.MovieReviewResponse
import com.katilijiwo.movieplot.data.remote.json.upcomingmoviejson.UpcomingMovieResponse

class Repository(
    private val service: PopularMovieService,
    private val movieDao: MovieDao
) {
    suspend fun fetPopularMovies(page: Int) : PopularMovieReponse {
        try {
            return service.fetchPopularMovies(page).await()
        } catch (ex :Exception){
            throw ex
        }
    }

    suspend fun fetchUpcomingMovie(page: Int): UpcomingMovieResponse {
        try {
            return service.fetchUpcomingMovie(page).await()
        } catch (ex: Exception){
            throw ex
        }
    }

    suspend fun fetchMovieDetail(movieID: Int): MovieDetailResponse {
        try {
            return service.fetchMovieDetail(movieID).await()
        } catch (ex: Exception){
            throw ex
        }
    }

    suspend fun fetchMovieReview(movieID: Int, page: Int): MovieReviewResponse {
        try {
            return service.fetchMovieReview(movieID, page).await()
        } catch (ex: Exception){
            throw ex
        }
    }

    fun getAllMovieByDate(): LiveData<List<Movie>> = movieDao.getAllMovieSortedByDate()

    fun getMovieByID(id: Int): LiveData<Movie?> = movieDao.getMovieByID(id)

    suspend fun insertMovie(movie: Movie) {
        return movieDao.insertMovie(movie)
    }

    suspend fun deleteMovie(movieID: Int) {
        return movieDao.deleteMovie(movieID)
    }

}