package com.example.jogtrackerapp.allRuns


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.jogtrackerapp.activity.MainActivity

import com.example.jogtrackerapp.R
import com.example.jogtrackerapp.app.JogApplication
import kotlinx.android.synthetic.main.fragment_all_runs.*
import kotlinx.android.synthetic.main.fragment_all_runs.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class AllRunsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel:AllRunsViewModel
    lateinit var recyclerAdaptor:RecyclerAdaptor
    lateinit var constraintLayout:ConstraintLayout
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_runs, container, false)

        ((activity as MainActivity).application as JogApplication).appComponent.inject(this)
        constraintLayout = view.constraintLayout
        progressBar = view.progressBar

        viewModel = activity?.run {
            ViewModelProvider(this, viewModelFactory)[AllRunsViewModel::class.java]
        }?: throw Exception("Invalid Activity")

        recyclerAdaptor = RecyclerAdaptor(){it ->
            (activity as MainActivity).fragmentController.openAddNewFragment()
            viewModel.setJog(it)
        }

        view.list.adapter = recyclerAdaptor

        viewModel.allRuns.observe(viewLifecycleOwner, Observer {
            if(it != null){
                recyclerAdaptor.submitList(it)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {

            if(it == true){
                startLoading()
            }else{
                stopLoading()
            }
        })

        viewModel.loadAllRuns()


        view.addNew.setOnClickListener{
            (activity as MainActivity).fragmentController.openAddNewFragment()
        }

        return view
    }

    fun startLoading(){
        constraintLayout.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    fun stopLoading(){
        constraintLayout.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }


}
