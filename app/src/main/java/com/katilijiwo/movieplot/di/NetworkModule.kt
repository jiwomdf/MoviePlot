package com.katilijiwo.movieplot.di

import com.katilijiwo.movieplot.BuildConfig
import com.katilijiwo.movieplot.data.remote.Api
import com.katilijiwo.movieplot.data.remote.PopularMovieService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { headerInterceptor() }
    single { okhttpClient(get()) }
    single { retrofit(get()) }
    single { apiService(get()) }
    single { createPopularMovieService(get()) }
}

fun createPopularMovieService(
    api: Api
) : PopularMovieService = PopularMovieService(api)

fun apiService(
    retrofit: Retrofit
) : Api =
    retrofit.create(Api::class.java)

fun retrofit(
    okHttpClient: OkHttpClient
) : Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun okhttpClient(
    headerInterceptor: Interceptor
) : OkHttpClient =
    OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(headerInterceptor)
        .build()

fun headerInterceptor() : Interceptor =
    Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url().newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .addQueryParameter("language", Locale.getDefault().language)
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }