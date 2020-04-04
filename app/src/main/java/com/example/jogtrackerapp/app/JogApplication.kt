package com.example.jogtrackerapp.app

import android.app.Application
import com.example.jogtrackerapp.di.AppComponent
import com.example.jogtrackerapp.di.DaggerAppComponent

class JogApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(app: Application): AppComponent =
        DaggerAppComponent.builder()
            .application(app)
            .build()
}