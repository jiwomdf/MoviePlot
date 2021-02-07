package com.katilijiwo.movieplot.ui.savedmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katilijiwo.movieplot.data.Repository
import com.katilijiwo.movieplot.data.RepositoryImpl
import kotlinx.coroutines.launch

class SavedMovieViewModel(
    private val repository: Repository
): ViewModel() {

    val savedMovies = repository.getAllMovieByDate()

    fun deleteMovie(movieID: Int){
        viewModelScope.launch {
            repository.deleteMovie(movieID)
        }
    }

}