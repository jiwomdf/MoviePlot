package com.katilijiwo.movieplot.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.katilijiwo.movieplot.data.local.MoviePlotRoom
import com.katilijiwo.movieplot.data.local.dao.MovieDao
import com.katilijiwo.movieplot.util.Constant
import com.katilijiwo.movieplot.util.Constant.DATABASE_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideMovieDao(get()) }
}

fun provideDatabase(context: Application): MoviePlotRoom {
    return Room.databaseBuilder(
        context,
        MoviePlotRoom::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}

fun provideMovieDao(db: MoviePlotRoom): MovieDao {
    return db.movieDao()
}