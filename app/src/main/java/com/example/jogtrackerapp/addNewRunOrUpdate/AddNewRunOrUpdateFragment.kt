package com.example.jogtrackerapp.addNewRunOrUpdate


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
import com.example.jogtrackerapp.logging.LoggingViewModel
import com.example.jogtrackerapp.netwok.models.api.JogsItem

/**
 * A simple [Fragment] subclass.
 */
class AddNewRunOrUpdateFragment : Fragment() {

    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel:AddNewRunOrUpdateFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_new_run, container, false)

        ((activity as MainActivity).application as JogApplication).appComponent.inject(this)

        viewModel =  ViewModelProvider(this, viewModelFactory)
        .get(AddNewRunOrUpdateFragmentViewModel::class.java)

        val jogsItem = arguments?.getParcelable(KEY_TEXT) as JogsItem?
        if(jogsItem != null){
            viewModel.setJog(jogsItem)
        }



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
        //1 - Ok add
        //2 - No date
        //3 - Network error
        //4 - Ok update
        viewModel.operationCode.observe(viewLifecycleOwner, Observer { it ->
            Log.d("WTF", "CODE:$it")
            when(it){
                1 -> showToast(view.context, "New jog added")
                2 -> showToast(view.context, "Please pick date")
                3 -> showToast(view.context, "Network Error")
                4 -> showToast(view.context, "Updated jog")
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

        viewModel.distance.observe(viewLifecycleOwner, Observer {
            if(it != null){
                view.distance.setText(it)
            }
        })

        viewModel.time.observe(viewLifecycleOwner, Observer {
            if(it != null){
                view.time.setText(it)
            }
        })

        return view
    }
    

    fun showToast(context: Context, string: String){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }


    companion object {
        private val KEY_TEXT = "special_text"

        fun newInstance(jogsItem: JogsItem?): AddNewRunOrUpdateFragment {
            val fragment = AddNewRunOrUpdateFragment()
            val args = Bundle()
            args.putParcelable(KEY_TEXT, jogsItem)
            fragment.arguments = args
            return fragment

        }

    }

}
