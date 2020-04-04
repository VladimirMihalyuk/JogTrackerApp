package com.example.jogtrackerapp.app

import android.app.Application
import android.content.SharedPreferences

private val TOKEN_KEY = "TOKEN_KEY"
private val SPNAME = "MyPref"
private val LOGIN_KEY = "LOGIN_KEY"

class SharedPreferencesWrapper(private val application: Application){

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = application.getSharedPreferences(SPNAME, 0)
    }

    fun setToken(token: String){
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(): String = sharedPreferences.getString(TOKEN_KEY, null) ?: ""

    fun setLogin(login: String){
        val editor = sharedPreferences.edit()
        editor.putString(LOGIN_KEY, login)
        editor.apply()
    }

    fun getLogin(): String =  sharedPreferences.getString(LOGIN_KEY, null) ?: ""
}