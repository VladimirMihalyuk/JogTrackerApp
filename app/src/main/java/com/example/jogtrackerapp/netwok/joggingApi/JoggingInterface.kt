package com.example.jogtrackerapp.netwok.joggingApi

import com.example.jogtrackerapp.netwok.models.api.ResponseJogs
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface JoggingInterface {
    @GET("v1/data/sync")
    fun getData(): Deferred<Response<ResponseJogs>>

    @POST("v1/data/jog")
    @FormUrlEncoded
    fun postJog(@Field("date") date: String,
                 @Field("time") time: Int,
                 @Field("distance") distance: Float): Deferred<Response<ResponseBody>>

    @PUT("v1/data/jog")
    @FormUrlEncoded
    fun putJog(@Field("date") date: String,
               @Field("time") time: Int,
               @Field("distance") distance: Float,
               @Field("jog_id") jog_id: Int,
               @Field("user_id") user_id: String): Deferred<Response<ResponseBody>>
}