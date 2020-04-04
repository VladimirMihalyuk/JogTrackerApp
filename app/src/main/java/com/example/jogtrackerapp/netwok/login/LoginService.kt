package com.example.jogtrackerapp.netwok.login

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val BASE_URL = "https://jogtracker.herokuapp.com/api/"


class LoginService {
    companion object{
        fun provideApi(): LoginInterface =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(LoginInterface::class.java)
    }
}