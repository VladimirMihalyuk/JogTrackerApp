package com.example.jogtrackerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.jogtrackerapp.app.JogApplication
import com.example.jogtrackerapp.netwok.joggingApi.JoggingInterface
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var joggingService: JoggingInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as JogApplication).appComponent.inject(this)

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
