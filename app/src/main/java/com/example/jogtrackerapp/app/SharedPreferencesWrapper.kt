package com.example.jogtrackerapp.app

import android.app.Application
import android.content.SharedPreferences

private val TOKEN_KEY = "TOKEN_KEY"
private val SHPNAME = "MyPref"

class SharedPreferencesWrapper(private val application: Application){

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = application.getSharedPreferences(SHPNAME, 0)
    }

    fun setToken(token: String){
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(): String = sharedPreferences.getString(TOKEN_KEY, null) ?: ""
}