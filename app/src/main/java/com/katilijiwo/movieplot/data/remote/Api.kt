package com.katilijiwo.movieplot.data.remote

import com.katilijiwo.movieplot.data.remote.json.moviedetail.MovieDetailResponse
import com.katilijiwo.movieplot.data.remote.json.popularmoviejson.PopularMovieReponse
import com.katilijiwo.movieplot.data.remote.json.reviewmoviejson.MovieReviewResponse
import com.katilijiwo.movieplot.data.remote.json.upcomingmoviejson.UpcomingMovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ) : Response<PopularMovieReponse>

    @GET("3/movie/upcoming")
    suspend fun fetchUpcomingMovies(
        @Query("page") page: Int
    ) : Response<UpcomingMovieResponse>

    @GET("3/movie/{movie_id}")
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieID: Int
    ): Response<MovieDetailResponse>

    @GET("3/movie/{movie_id}/reviews")
    suspend fun fetchMovieReview(
        @Path("movie_id") movieID: Int,
        @Query("page") page: Int
    ): Response<MovieReviewResponse>

}