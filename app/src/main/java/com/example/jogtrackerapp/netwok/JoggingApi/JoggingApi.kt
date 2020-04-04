package com.example.jogtrackerapp.netwok.JoggingApi

import com.example.jogtrackerapp.netwok.models.api.ResponseJogs
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface JoggingApi {
    @GET("v1/data/sync")
    fun getData(@Header("Authorization") authorization: String):
            Deferred<Response<ResponseJogs>>
}