package com.example.jogtrackerapp.netwok.joggingApi

import com.example.jogtrackerapp.netwok.models.api.ResponseJogs
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface JoggingInterface {
    @GET("v1/data/sync")
    fun getData(): Deferred<Response<ResponseJogs>>
}