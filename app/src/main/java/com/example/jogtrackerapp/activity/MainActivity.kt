package com.example.jogtrackerapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.jogtrackerapp.R
import com.example.jogtrackerapp.app.SharedPreferencesWrapper
import com.example.jogtrackerapp.logging.LoggingFragment


class MainActivity : AppCompatActivity() {
    val fragmentController: FragmentController =
        FragmentController()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentController.nextFragment.observe(this, Observer<Fragment> {
            if (it is LoggingFragment) {
                showDefaultFragment(it)
            } else {
                showFragment(it)
            }
        })


    }

    private fun showDefaultFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.base_fragment_layout, fragment
            )
            .commit()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.base_fragment_layout, fragment
            )
            .commit()
    }

}
