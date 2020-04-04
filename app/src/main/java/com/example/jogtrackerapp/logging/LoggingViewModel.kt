package com.example.jogtrackerapp.logging

import androidx.lifecycle.ViewModel
import com.example.jogtrackerapp.app.SharedPreferencesWrapper
import com.example.jogtrackerapp.netwok.login.LoginInterface
import javax.inject.Inject

class LoggingViewModel @Inject constructor (
    private val loginInterface: LoginInterface,
    private val sharedPreferencesWrapper: SharedPreferencesWrapper): ViewModel() {

}