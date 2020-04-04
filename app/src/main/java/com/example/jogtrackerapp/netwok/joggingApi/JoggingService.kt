package com.example.jogtrackerapp.netwok.joggingApi

import com.example.jogtrackerapp.app.SharedPreferencesWrapper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val BASE_URL = "https://jogtracker.herokuapp.com/api/"

class JoggingService{
    companion object{

        fun okHttpClient(spWrapper: SharedPreferencesWrapper): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(TokenInterceptor(spWrapper))
            return httpClient.build()
        }

        fun provideApi(httpClient: OkHttpClient): JoggingApi =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build()
                .create(JoggingApi::class.java)
    }
}

class TokenInterceptor constructor(private val spWrapper: SharedPreferencesWrapper) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
            val originalHttpUrl = original.url
        val requestBuilder = original.newBuilder()
            .addHeader("Authorization", spWrapper.getToken())
            .url(originalHttpUrl)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}