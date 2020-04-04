package com.example.jogtrackerapp.netwok.JoggingApi

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val BASE_URL = "https://jogtracker.herokuapp.com/api/"

class JoggingService{
    companion object{

        fun provideApi(): JoggingApi =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .build()
                .create(JoggingApi::class.java)
    }
}