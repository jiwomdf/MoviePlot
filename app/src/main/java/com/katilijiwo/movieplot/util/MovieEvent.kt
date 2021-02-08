package com.katilijiwo.movieplot.util

sealed class MovieEvent<out T> {
    class Success<out T>(val data: T): MovieEvent<T>()
    class NotFound<out T>(val message: String = "Data not found") : MovieEvent<T>()
    class Error(val message: String): MovieEvent<Nothing>()
    object Loading: MovieEvent<Nothing>()
}