package com.katilijiwo.movieplot.ui.favoritemovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katilijiwo.movieplot.data.Repository
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(
    private val repository: Repository
): ViewModel() {

    val savedMovies = repository.getAllMovieByDate()

    fun deleteMovie(movieID: Int){
        viewModelScope.launch {
            repository.deleteMovie(movieID)
        }
    }

}