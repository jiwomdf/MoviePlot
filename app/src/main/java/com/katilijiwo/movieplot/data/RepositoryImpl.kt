package com.katilijiwo.movieplot.data

import androidx.lifecycle.LiveData
import com.katilijiwo.movieplot.data.local.dao.MovieDao
import com.katilijiwo.movieplot.data.local.entity.Movie
import com.katilijiwo.movieplot.data.remote.PopularMovieService
import com.katilijiwo.movieplot.data.remote.json.moviedetail.MovieDetailResponse
import com.katilijiwo.movieplot.data.remote.json.popularmoviejson.PopularMovieReponse
import com.katilijiwo.movieplot.data.remote.json.reviewmoviejson.MovieReviewResponse
import com.katilijiwo.movieplot.data.remote.json.upcomingmoviejson.UpcomingMovieResponse

class RepositoryImpl(
    private val service: PopularMovieService,
    private val movieDao: MovieDao
): Repository {
    override suspend fun fetPopularMovies(page: Int) : PopularMovieReponse {
        try {
            return service.fetchPopularMovies(page)
        } catch (ex :Exception){
            throw ex
        }
    }

    override suspend fun fetchUpcomingMovie(page: Int): UpcomingMovieResponse {
        try {
            return service.fetchUpcomingMovie(page)
        } catch (ex: Exception){
            throw ex
        }
    }

    override suspend fun fetchMovieDetail(movieID: Int): MovieDetailResponse {
        try {
            return service.fetchMovieDetail(movieID)
        } catch (ex: Exception){
            throw ex
        }
    }

    override suspend fun fetchMovieReview(movieID: Int, page: Int): MovieReviewResponse {
        try {
            return service.fetchMovieReview(movieID, page)
        } catch (ex: Exception){
            throw ex
        }
    }

    override fun getAllMovieByDate(): LiveData<List<Movie>> = movieDao.getAllMovieSortedByDate()

    override fun getMovieByID(id: Int): LiveData<Movie?> = movieDao.getMovieByID(id)

    override suspend fun insertMovie(movie: Movie) {
        return movieDao.insertMovie(movie)
    }

    override suspend fun deleteMovie(movieID: Int) {
        return movieDao.deleteMovie(movieID)
    }

}