package com.example.jogtrackerapp.netwok.joggingApi

import com.example.jogtrackerapp.netwok.models.api.ResponseJogs
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface JoggingInterface {
    @GET("v1/data/sync")
    fun getData(): Deferred<Response<ResponseJogs>>

    @POST("v1/data/jog")
    @FormUrlEncoded
    fun postJog(@Field("date") date: String,
                 @Field("time") time: Int,
                 @Field("distance") distance: Float): Deferred<Response<ResponseBody>>
}