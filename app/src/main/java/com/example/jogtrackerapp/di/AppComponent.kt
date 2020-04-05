package com.example.jogtrackerapp.di

import android.app.Application
import com.example.jogtrackerapp.addNewRun.AddNewRunFragment
import com.example.jogtrackerapp.allRuns.AllRunsFragment
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
    fun inject(target: LoggingFragment)

    fun inject(target: AllRunsFragment)

    fun inject(target: AddNewRunFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
        @BindsInstance
        fun application(application: Application): Builder
    }
}