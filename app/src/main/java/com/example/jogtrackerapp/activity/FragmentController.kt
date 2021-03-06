package com.example.jogtrackerapp.activity

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jogtrackerapp.addNewRunOrUpdate.AddNewRunOrUpdateFragment
import com.example.jogtrackerapp.allRuns.AllRunsFragment
import com.example.jogtrackerapp.logging.LoggingFragment
import com.example.jogtrackerapp.netwok.models.api.JogsItem

class FragmentController {
    private val goToNextFragment: MutableLiveData<Fragment> = MutableLiveData()
    val nextFragment: LiveData<Fragment> = goToNextFragment

    init{
        pushNextFragment(LoggingFragment())
    }


    private fun pushNextFragment(newFragment: Fragment) {
        goToNextFragment.value = newFragment
    }

    fun openAllRuns() {
        pushNextFragment(AllRunsFragment())
    }

    fun openAddNewFragment(jogsItem: JogsItem?){
        pushNextFragment(AddNewRunOrUpdateFragment.newInstance(jogsItem))
    }


}