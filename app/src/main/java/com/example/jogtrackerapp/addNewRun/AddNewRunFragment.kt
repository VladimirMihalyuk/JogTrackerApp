package com.example.jogtrackerapp.addNewRun


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.jogtrackerapp.R
import com.example.jogtrackerapp.activity.MainActivity
import com.example.jogtrackerapp.allRuns.AllRunsViewModel
import com.example.jogtrackerapp.app.JogApplication
import kotlinx.android.synthetic.main.fragment_add_new_run.view.*
import java.util.*
import javax.inject.Inject
import androidx.lifecycle.Observer

/**
 * A simple [Fragment] subclass.
 */
class AddNewRunFragment : Fragment() {

    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel:AllRunsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_new_run, container, false)

        ((activity as MainActivity).application as JogApplication).appComponent.inject(this)

        viewModel = activity?.run {
            ViewModelProvider(this, viewModelFactory)[AllRunsViewModel::class.java]
        }?: throw Exception("Invalid Activity")

        viewModel.dateString.observe(viewLifecycleOwner, Observer {
            view.dateText.text = it
        })


        view.pickDate.setOnClickListener {
            val dpd = DatePickerDialog(view.context,
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
                viewModel.setDateString(year, month + 1, day)
            }, year, month, day)
            dpd.show()
        }

        view.addUpdate.setOnClickListener {
            viewModel.addNewJog(view.time.text.toString(), view.distance.text.toString())
        }


        return view
    }


}
