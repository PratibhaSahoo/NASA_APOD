package com.example.nasaapod.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasaapod.databinding.ActivityMainBinding

class ApodActivity : AppCompatActivity() {

    private var activityMainBinding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding!!.root)
    }

}