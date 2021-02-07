package com.katilijiwo.movieplot.base

import android.util.Log
import com.google.gson.Gson
import com.katilijiwo.movieplot.BuildConfig
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.katilijiwo.movieplot.util.Resource
import com.katilijiwo.movieplot.data.remote.exceptions.NoInternetException
import com.katilijiwo.movieplot.data.remote.exceptions.NotFoundException
import com.katilijiwo.movieplot.data.remote.exceptions.UnAuthorizedException
import com.katilijiwo.movieplot.data.remote.exceptions.UnKnownException
import retrofit2.Call
import java.io.IOException

abstract class BaseService {

    companion object {
        fun<T > execute(call : Call<T>) : T {
            try{
                val response = call.execute()
                return when(response.isSuccessful){
                    true -> {
                        if(BuildConfig.BUILD_TYPE == ("debug"))
                            Log.d("<RES>", Gson().toJson(response.body()!!))
                        response.body()!!
                    }
                    false -> {
                        if(BuildConfig.BUILD_TYPE == "debug")
                            Log.d("<RES>", response.message())
                        throw HttpException(response)
                    }
                }
            }
            catch (e : Exception){
                if(BuildConfig.BUILD_TYPE == "debug")
                    e.message?.let {
                        Log.d("<RES>", it)
                    }
                throw e
            }
        }
    }

}