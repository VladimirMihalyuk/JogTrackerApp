package com.example.jogtrackerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.jogtrackerapp.logging.LoggingFragment


class MainActivity : AppCompatActivity() {
    val fragmentController: FragmentController = FragmentController()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentController.nextFragment.observe(this, Observer<Fragment> {
            if (it is LoggingFragment) {
                showDefaultFragment(it)
                Log.d("WTF", "SHIT")
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
