package com.example.jogtrackerapp

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jogtrackerapp.logging.LoggingFragment

class FragmentController {
    private val goToNextFragment: MutableLiveData<Fragment> = MutableLiveData()
    val nextFragment: LiveData<Fragment> = goToNextFragment

    init{
        pushNextFragment(LoggingFragment())
    }


    private fun pushNextFragment(newFragment: Fragment) {
        goToNextFragment.value = newFragment
    }

//    fun openLogin() {
//        pushNextFragment(LoginFragment())
//    }


}