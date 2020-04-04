package com.example.jogtrackerapp.di

import android.app.Application
import com.example.jogtrackerapp.app.SharedPreferencesWrapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class SharedPreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferencesWrapper(application: Application)
            = SharedPreferencesWrapper(application)
}