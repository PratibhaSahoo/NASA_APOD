package com.example.nasaapod.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtil {
    fun isInternetAvailable(applicationContext: Context): Boolean {
        (applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) return this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET
            ) ?: false else (@Suppress("DEPRECATION")
            return this.activeNetworkInfo?.isConnected ?: false)
        }
    }
}