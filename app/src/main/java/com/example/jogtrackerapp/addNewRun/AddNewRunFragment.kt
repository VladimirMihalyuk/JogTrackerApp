package com.example.jogtrackerapp.addNewRun


import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        //0 - Empty
        //1 - Ok
        //2 - No date
        //3 - Network error
        viewModel.operationCode.observe(viewLifecycleOwner, Observer { it ->
            Log.d("WTF", "CODE:$it")
            when(it){
                1 -> showToast(view.context, "New jog added")
                2 -> showToast(view.context, "Please pick date")
                3 -> showToast(view.context, "Network Error")
                else -> {}
            }
            if(it > 0)
                viewModel.resetCode()

        })

        viewModel.validTime.observe(viewLifecycleOwner, Observer {
            if(it){
                view.timeLayout.isErrorEnabled = false
            }else{
                view.timeLayout.error = "Wrong value"
            }
        })

        viewModel.validDistance.observe(viewLifecycleOwner, Observer{
            if(it){
                view.distanceLayout.isErrorEnabled = false
            }else{
                view.distanceLayout.error = "Wrong value"
            }
        })


        return view
    }

    fun showToast(context: Context, string: String){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }


}
