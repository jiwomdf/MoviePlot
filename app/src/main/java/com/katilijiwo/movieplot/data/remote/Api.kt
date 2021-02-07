package com.katilijiwo.movieplot.data.remote

import com.katilijiwo.movieplot.data.remote.json.moviedetail.MovieDetailResponse
import com.katilijiwo.movieplot.data.remote.json.popularmoviejson.PopularMovieReponse
import com.katilijiwo.movieplot.data.remote.json.reviewmoviejson.MovieReviewResponse
import com.katilijiwo.movieplot.data.remote.json.upcomingmoviejson.UpcomingMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("3/movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int
    ) : Call<PopularMovieReponse>

    @GET("3/movie/upcoming")
    fun fetchUpcomingMovies(
        @Query("page") page: Int
    ) : Call<UpcomingMovieResponse>

    @GET("3/movie/{movie_id}")
    fun fetchMovieDetail(
        @Path("movie_id") movieID: Int
    ): Call<MovieDetailResponse>

    @GET("3/movie/{movie_id}/reviews")
    fun fetchMovieReview(
        @Path("movie_id") movieID: Int,
        @Query("page") page: Int
    ): Call<MovieReviewResponse>

}