package com.katilijiwo.movieplot.util

sealed class MovieEvent<out T> {
    class Success<out T>(val data: T): MovieEvent<T>()
    class Error(val message: String = "response is empty"): MovieEvent<Nothing>()
    object Loading: MovieEvent<Nothing>()
}