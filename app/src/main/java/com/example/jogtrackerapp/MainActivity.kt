package com.example.jogtrackerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.jogtrackerapp.app.SharedPreferencesWrapper
import com.example.jogtrackerapp.netwok.joggingApi.JoggingService
import com.example.jogtrackerapp.netwok.login.LoginService
import com.example.jogtrackerapp.netwok.models.login.ResponseToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = LoginService.provideApi()

        val spWrapper = SharedPreferencesWrapper(application)

        service.getToken("hello").enqueue(
                object : Callback<ResponseToken> {
                    override fun onFailure(call: Call<ResponseToken>?, t: Throwable?) {}

                    override fun onResponse(call: Call<ResponseToken>?,
                                            response: Response<ResponseToken>?) {

                        if(response?.isSuccessful == true){
                            val body =  response.body()
                            if(body?.response?.accessToken != null
                                && body.response.tokenType != null) {

                                spWrapper.setToken(body.response.tokenType +
                                        " " + body.response.accessToken)

                                val joggingService
                                        = JoggingService.provideApi(
                                    JoggingService.okHttpClient(spWrapper))
                                GlobalScope.launch {
                                    val responseData
                                            = joggingService.getData().await()
                                    if(responseData.isSuccessful){
                                        Log.d("WTF", "${responseData.body()}")
                                    }else{
                                        Log.d("WTF", "DAta:${responseData?.code()}")
                                    }

                                }
                            }
                        }else{
                            Log.d("WTF", "${response?.code()}")
                        }
                    }
                })
    }
}
