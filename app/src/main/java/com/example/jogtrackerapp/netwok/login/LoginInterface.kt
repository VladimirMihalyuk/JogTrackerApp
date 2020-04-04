package com.example.jogtrackerapp.netwok.login

import com.example.jogtrackerapp.netwok.models.login.ResponseToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST



interface LoginInterface {
    @POST("v1/auth/uuidLogin")
    @FormUrlEncoded
    fun getToken(@Field("uuid") uuid: String): Call<ResponseToken>
}