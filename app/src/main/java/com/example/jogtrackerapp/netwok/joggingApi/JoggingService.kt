package com.example.jogtrackerapp.netwok.joggingApi

import android.util.Log
import com.example.jogtrackerapp.app.SharedPreferencesWrapper
import com.example.jogtrackerapp.netwok.login.LoginInterface
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



private val BASE_URL = "https://jogtracker.herokuapp.com/api/"

class JoggingService{
    companion object{

        fun okHttpClient(loginInterface: LoginInterface,
                         spWrapper: SharedPreferencesWrapper): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(TokenInterceptor(spWrapper))
            httpClient.authenticator(TokenAuthenticator(loginInterface, spWrapper))
            return httpClient.build()
        }

        fun provideApi(httpClient: OkHttpClient): JoggingInterface =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build()
                .create(JoggingInterface::class.java)
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

class TokenAuthenticator constructor(private val loginInterface: LoginInterface,
                                     private val spWrapper: SharedPreferencesWrapper): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d("WTF", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
        val login = spWrapper.getLogin()
        val tokenResp = loginInterface.getToken(login).execute()

        if(tokenResp.isSuccessful && tokenResp.body()?.response?.tokenType != null
            && tokenResp.body()?.response?.accessToken != null){
            val token =   tokenResp.body()?.response?.tokenType +
                    " " +  tokenResp.body()?.response?.accessToken

            spWrapper.setToken(token)

            return response.request.newBuilder()
                .header("Authorization", token).build()
        }
        return null
    }
}