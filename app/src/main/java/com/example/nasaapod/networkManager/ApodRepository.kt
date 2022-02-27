package com.example.nasaapod.networkManager

import android.content.Context
import android.util.Log
import com.example.nasaapod.database.entities.Favorites
import com.example.nasaapod.database.roomDB.ApodRoomDatabase
import com.example.nasaapod.model.Apod
import com.example.nasaapod.utils.NetworkUtil
import com.google.android.material.snackbar.Snackbar
import retrofit2.Response

class ApodRepository(
    val retrofitService: RetrofitService,
    val apodRoomDatabase: ApodRoomDatabase,
    val appContext: Context
) {
    private var response: Response<Apod>? = null
    private val API_KEY = "URBegYiXx26bvYXbMLYMGdbaiUgreK6q8FhGvvaQ"

    suspend fun getApodByDate(date: String): Response<Apod> {
        if (NetworkUtil.isInternetAvailable(appContext)) {
            response = retrofitService.getApodByDate(date, API_KEY)
            response!!.body()?.let {
                apodRoomDatabase.apodDao().insertApod(it)
            }
        } else {
            val apodDbData = apodRoomDatabase.apodDao().getApodByDate(date)
            response = apodDbData as Response<Apod>
        }
        return response as Response<Apod>
    }

    suspend fun insertFavorite(favorites: Favorites) {
        apodRoomDatabase.apodDao().insertFavorite(favorites)
    }

    fun getFavoritesList() = apodRoomDatabase.apodDao().getAllFavorites()
}