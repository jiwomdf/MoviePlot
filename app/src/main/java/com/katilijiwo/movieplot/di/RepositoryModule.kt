package com.katilijiwo.movieplot.di

import com.katilijiwo.movieplot.data.Repository
import com.katilijiwo.movieplot.data.RepositoryImpl
import com.katilijiwo.movieplot.data.local.dao.MovieDao
import com.katilijiwo.movieplot.data.remote.PopularMovieService
import org.koin.dsl.module

val repositoryModule = module {
    single { createRepository(get(), get()) }
}

fun createRepository(
    popularMovieService: PopularMovieService,
    movieDao: MovieDao
) : Repository = RepositoryImpl(popularMovieService, movieDao)