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

    //0 - Empty
    //1 - Ok
    //2 - No date
    //3 - Network error
    private val _operationCode = MutableLiveData<Int>()
    val operationCode:LiveData<Int>
        get() = _operationCode

    fun resetCode(){
        _operationCode.value = 0
    }

    private val _validTime = MutableLiveData<Boolean>()
    val validTime: LiveData<Boolean>
        get() = _validTime

    private val _validDistance = MutableLiveData<Boolean>()
    val validDistance: LiveData<Boolean>
        get() = _validDistance

    fun addNewJog(timeS: String, distanceS: String){
        var valid = true
        var time = 0
        var distance = 0F

        Log.d("WTF", "${dateString.value}")

        if (_dateString.value?.isNotEmpty() != true){
            _operationCode.value = 2
            valid = false
        }

        try{
            time = timeS.toInt()
            _validTime.value = true
        }catch (e: NumberFormatException){
            _validTime.value  =false
            valid = false
        }

        try{
            distance = distanceS.toFloat()
            _validDistance.value = true
        }catch (e: NumberFormatException){
            _validDistance.value = false
            valid = false
        }

        if(valid){
            viewModelScope.launch(Dispatchers.IO) {
                val response
                        = joggingInterface.postJog("${(_dateString.value)}",
                    time, distance).await()
                if(response.isSuccessful){
                    _operationCode.postValue(1)
                }else{
                    _operationCode.postValue(3)
                }
            }
        }
    }
}