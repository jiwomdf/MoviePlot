package com.katilijiwo.movieplot.ui.savedmovie

import androidx.lifecycle.ViewModel
import com.katilijiwo.movieplot.data.Repository

class SavedMovieViewModel(
    private val repository: Repository
): ViewModel() {

    val savedMovies = repository.getAllMovieByDate()

}