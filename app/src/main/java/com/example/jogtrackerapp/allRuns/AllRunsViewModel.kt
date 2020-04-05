package com.example.jogtrackerapp.allRuns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun loadAllRuns(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = joggingInterface.getData().await()
            if(response.isSuccessful){
                response.body()?.response?.jogs?.let{
                    _allRuns.postValue(it)
                }
            }
        }
    }
}