package com.example.jogtrackerapp.logging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jogtrackerapp.app.SharedPreferencesWrapper
import com.example.jogtrackerapp.netwok.login.LoginInterface
import com.example.jogtrackerapp.netwok.models.login.ResponseToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoggingViewModel @Inject constructor (
    private val loginInterface: LoginInterface,
    private val sharedPreferencesWrapper: SharedPreferencesWrapper): ViewModel() {


    private val _error = MutableLiveData<Boolean>()
    val error:LiveData<Boolean>
        get() = _error

    fun resetError(){
        _error.value = false
    }

    private val _nextPage = MutableLiveData<Boolean>()
    val nextPage:LiveData<Boolean>
        get() = _error

    fun resetNextPage(){
        _error.value = false
    }

    fun logIn(uuid: String){
        val call = loginInterface.getToken(uuid)
        call.enqueue(object : Callback<ResponseToken> {
            override fun onFailure(call: Call<ResponseToken>?, t: Throwable?) {
                _error.value = true
            }
            override fun onResponse(call: Call<ResponseToken>?,
                                    response: Response<ResponseToken>?) {
                if(response?.isSuccessful == true
                    && response.body()?.response?.accessToken != null
                    && response.body()?.response?.tokenType != null){

                    _nextPage.value = true
                    sharedPreferencesWrapper.setLogin(uuid)
                    sharedPreferencesWrapper.setToken(response.body()?.response?.tokenType
                            + " " + response.body()?.response?.accessToken)

                }else{
                    _error.value = true
                }
            }
        })
    }
}