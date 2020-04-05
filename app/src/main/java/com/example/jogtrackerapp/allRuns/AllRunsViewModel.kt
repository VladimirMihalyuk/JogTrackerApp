package com.example.jogtrackerapp.allRuns

import android.util.Log
import androidx.lifecycle.*
import com.example.jogtrackerapp.app.dateToMillis
import com.example.jogtrackerapp.app.getDate
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
    //1 - Ok add
    //2 - No date
    //3 - Network error
    //4 - Ok update
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

    private val jogItem = MutableLiveData<JogsItem>()
    fun setJog(jogsItem: JogsItem){
        jogItem.value = jogsItem
        _dateString.value = ((jogsItem.date ?: 0) * 1000).getDate()
    }

    val distance:LiveData<String> =
        Transformations.map(jogItem){it ->
            (it?.distance ?: 0).toString()
        }

    val time: LiveData<String> =
        Transformations.map(jogItem){
            (it?.time ?: 0).toString()
        }

    fun clearValues(){
        jogItem.value = null
        _validTime.value = true
        _validDistance.value = true
        _dateString.value = "--/--/----"
    }


    fun addNewJog(timeS: String, distanceS: String){
        var valid = true
        var time = 0
        var distance = 0F

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
            if(jogItem.value == null){
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
            }else {
                viewModelScope.launch(Dispatchers.IO) {
                    val jogId = (jogItem.value as JogsItem).id ?: 0
                    val userId = (jogItem.value as JogsItem).userId ?: ""
                    val response =
                        joggingInterface.putJog(
                            "${(_dateString.value)}",
                            time,
                            distance,
                            jogId,
                            userId).await()
                    if (response.isSuccessful) {
                        _operationCode.postValue(4)
                    } else {
                        _operationCode.postValue(3)

                    }
                }

            }

        }
    }
}