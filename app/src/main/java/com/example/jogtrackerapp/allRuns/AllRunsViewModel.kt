package com.example.jogtrackerapp.allRuns

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jogtrackerapp.app.dateToMillis
import com.example.jogtrackerapp.netwok.joggingApi.JoggingInterface
import com.example.jogtrackerapp.netwok.models.api.JogsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllRunsViewModel @Inject constructor(private val joggingInterface: JoggingInterface)
    : ViewModel() {


    private val _allRuns = MutableLiveData<List<JogsItem>>()
    val allRuns: LiveData<List<JogsItem>>
        get() = _allRuns

    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean>
        get() = _loading

    fun loadAllRuns(){
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = joggingInterface.getData().await()
            if(response.isSuccessful){
                response.body()?.response?.jogs?.let{
                    _allRuns.postValue(it)
                    _loading.postValue(false)
                }
            }
        }
    }


    private val _dateString = MutableLiveData<String>()
    val dateString: LiveData<String>
        get() = _dateString

    fun setDateString(year: Int, month: Int, day: Int){
        _dateString.value = "$day/$month/$year"
    }

    fun addNewJog(timeS: String, distanceS: String){
        val time = timeS.toInt()
        val distance = distanceS.toFloat()
        Log.d("WTF", "${_dateString.value}")

        viewModelScope.launch(Dispatchers.IO) {
            val response
                    = joggingInterface.postJog("${(_dateString.value)}",
                time, distance).await()
            if(response.isSuccessful){
                Log.d("WTF", "${response.body()?.string()}")
            }else{
                Log.d("WTF", "${response.code()} ${response.errorBody()?.string()}")
            }
        }
    }
}