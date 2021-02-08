package com.katilijiwo.movieplot.di

import com.katilijiwo.movieplot.ui.dashboard.DashboardViewModel
import com.katilijiwo.movieplot.ui.introduction.IntroductionViewModel
import com.katilijiwo.movieplot.ui.moviedetail.MovieDetailViewModel
import com.katilijiwo.movieplot.ui.favoritemovie.FavoriteMovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        IntroductionViewModel()
    }
    viewModel {
        DashboardViewModel(get())
    }
    viewModel {
        MovieDetailViewModel(get())
    }
    viewModel {
        FavoriteMovieViewModel(get())
    }
}