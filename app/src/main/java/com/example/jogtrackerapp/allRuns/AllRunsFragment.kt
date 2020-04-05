package com.example.jogtrackerapp.allRuns


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jogtrackerapp.activity.MainActivity

import com.example.jogtrackerapp.R
import com.example.jogtrackerapp.app.JogApplication
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class AllRunsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel:AllRunsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_runs, container, false)

        ((activity as MainActivity).application as JogApplication).appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AllRunsViewModel::class.java)


        viewModel.allRuns.observe(viewLifecycleOwner, Observer {
            Log.d("WTF", "$it")
        })

        viewModel.loadAllRuns()


        return view
    }


}
