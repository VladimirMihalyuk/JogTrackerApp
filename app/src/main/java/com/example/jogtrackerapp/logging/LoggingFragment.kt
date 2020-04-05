package com.example.jogtrackerapp.logging


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jogtrackerapp.activity.MainActivity

import com.example.jogtrackerapp.R
import com.example.jogtrackerapp.app.JogApplication
import kotlinx.android.synthetic.main.fragment_logging.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class LoggingFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: LoggingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logging, container, false)

        ((activity as MainActivity).application as JogApplication).appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(LoggingViewModel::class.java)

        view.button.setOnClickListener {
            viewModel.logIn(view.editText.text.toString())
        }


        viewModel.error.observe(viewLifecycleOwner, Observer { it ->
            if(it == true){
                Toast.makeText(view.context, "Wrong login", Toast.LENGTH_SHORT).show()
                viewModel.resetError()
            }
        })

        viewModel.nextPage.observe(viewLifecycleOwner, Observer { it ->
            if(it == true){
                (activity as MainActivity).fragmentController.openAllRuns()
                viewModel.resetNextPage()
            }

        })
        return view
    }
}
