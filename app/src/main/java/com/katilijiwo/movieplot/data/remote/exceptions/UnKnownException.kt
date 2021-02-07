package com.katilijiwo.movieplot.data.remote.exceptions

import java.lang.Exception

class UnKnownException : Exception() {

    override val message: String?
        get() = "Some Unknown Error Occurred"
}