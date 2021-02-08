package com.katilijiwo.movieplot.data.remote.exceptions

import java.io.IOException

class NoInternetException : IOException() {

    override val message: String?
        get() = "No Internet Connection"
}