package com.example.nasaapod.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasaapod.R
import com.example.nasaapod.databinding.ActivityMainBinding
import com.example.nasaapod.utils.NetworkUtil
import com.google.android.material.snackbar.Snackbar

class ApodActivity : AppCompatActivity() {

    private var activityMainBinding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding!!.root)
    }

    override fun onStart() {
        super.onStart()
        if(!NetworkUtil.isInternetAvailable(this)){
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.connect_to_internet), Snackbar.LENGTH_SHORT).show()
        }
    }
}