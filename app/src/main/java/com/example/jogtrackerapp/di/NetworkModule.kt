package com.example.jogtrackerapp.di

import com.example.jogtrackerapp.app.SharedPreferencesWrapper
import com.example.jogtrackerapp.netwok.joggingApi.JoggingService
import com.example.jogtrackerapp.netwok.login.LoginInterface
import com.example.jogtrackerapp.netwok.login.LoginService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideLoginInterface() = LoginService.provideApi()

    @Provides
    @Singleton
    fun provideOkHttpClient(loginInterface: LoginInterface,
                            spWrapper: SharedPreferencesWrapper)
            = JoggingService.okHttpClient(loginInterface, spWrapper)

    @Provides
    @Singleton
    fun provideJoggingApi(okHttpClient: OkHttpClient)
            = JoggingService.provideApi(okHttpClient)
}