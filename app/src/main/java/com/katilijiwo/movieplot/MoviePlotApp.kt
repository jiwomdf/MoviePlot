package com.katilijiwo.movieplot

import android.app.Application
import com.katilijiwo.movieplot.di.databaseModule
import com.katilijiwo.movieplot.di.networkModule
import com.katilijiwo.movieplot.di.repositoryModule
import com.katilijiwo.movieplot.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviePlotApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MoviePlotApp)
            modules(listOf(networkModule, repositoryModule, viewModelModule, databaseModule))
        }
    }

}