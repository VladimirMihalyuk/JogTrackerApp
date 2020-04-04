package com.example.jogtrackerapp.di

import android.app.Application
import com.example.jogtrackerapp.MainActivity
import com.example.jogtrackerapp.logging.LoggingFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    SharedPreferencesModule::class,
    NetworkModule::class,
    ViewModelModule::class])
interface AppComponent {

    fun inject(target: MainActivity)

    fun inject(target: LoggingFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
        @BindsInstance
        fun application(application: Application): Builder
    }
}