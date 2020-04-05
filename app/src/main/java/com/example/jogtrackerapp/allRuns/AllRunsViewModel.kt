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

}