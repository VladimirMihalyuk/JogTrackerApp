package com.example.jogtrackerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.jogtrackerapp.app.SharedPreferencesWrapper
import com.example.jogtrackerapp.netwok.joggingApi.JoggingService
import com.example.jogtrackerapp.netwok.login.LoginService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = LoginService.provideApi()

        val spWrapper = SharedPreferencesWrapper(application)
        spWrapper.setLogin("hello")

        val joggingService = JoggingService.provideApi(
            JoggingService.okHttpClient(service, spWrapper)
        )
        GlobalScope.launch {
            val responseData = joggingService.getData().await()
            if (responseData.isSuccessful) {
                Log.d("WTF", "${responseData.body()}")
            } else {
                Log.d("WTF", "DAta:${responseData.code()}")
            }

            val responseData2 = joggingService.getData().await()
            if (responseData2.isSuccessful) {
                Log.d("WTF", "${responseData.body()}")
            } else {
                Log.d("WTF", "DAta:${responseData.code()}")
            }
        }



    }



}
