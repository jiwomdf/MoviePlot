package com.katilijiwo.movieplot.base

import android.util.Log
import com.google.gson.Gson
import com.katilijiwo.movieplot.BuildConfig
import com.katilijiwo.movieplot.data.remote.exceptions.NoInternetException
import com.katilijiwo.movieplot.data.remote.exceptions.NotFoundException
import com.katilijiwo.movieplot.data.remote.exceptions.UnAuthorizedException
import com.katilijiwo.movieplot.data.remote.exceptions.UnKnownException
import com.katilijiwo.movieplot.util.Resource
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseService {

    protected suspend fun<T: Any> createCall(call: suspend () -> Response<T>) : Resource<T> {

        val response: Response<T>
        try {
            response = call.invoke()
        }catch (t: Throwable){
            t.printStackTrace()
            return Resource.Error(mapToNetworkError(t))
        }

        if (response.isSuccessful){
            if (response.body() != null){
                return Resource.Success(response.body()!!)
            }
        }
        else{
            val errorBody = response.errorBody()
            return if (errorBody != null){
                Resource.Error(mapApiException(response.code()))
            } else Resource.Error(mapApiException(0))
        }
        return Resource.Error(HttpException(response))
    }

    private fun mapApiException(code: Int): Exception {
        return when(code){
            HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException()
            HttpURLConnection.HTTP_UNAUTHORIZED -> UnAuthorizedException()
            else -> UnKnownException()
        }
    }

    private fun mapToNetworkError(t: Throwable): Exception {
        return when(t){
            is SocketTimeoutException
            -> SocketTimeoutException("Connection Timed Out")
            is UnknownHostException
            -> NoInternetException()
            else
            -> UnKnownException()

        }
    }

}