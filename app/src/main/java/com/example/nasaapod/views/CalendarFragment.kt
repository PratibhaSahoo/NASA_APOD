package com.example.nasaapod.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.nasaapod.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {
    private var calendarBinding : FragmentCalendarBinding? = null
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        calendarBinding = FragmentCalendarBinding.inflate(inflater, container, false)
        return calendarBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onDateChanged()
        navController = Navigation.findNavController(view)

    }

    private fun onDateChanged() {
        calendarBinding!!.calendarView.setOnDateChangeListener { calenderView, year, month, dayOfMonth ->
            val date = ""+ year + "-" + (month + 1) + "-" + dayOfMonth
            val action = CalendarFragmentDirections.actionCalendarFragmentToMainFragment()
            action.date = date
            navController.navigate(action)
        }
    }

}