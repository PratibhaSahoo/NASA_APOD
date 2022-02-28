package com.example.nasaapod.networkManager

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.nasaapod.database.entities.Favorites
import com.example.nasaapod.database.roomDB.ApodRoomDatabase
import com.example.nasaapod.model.Apod
import com.example.nasaapod.utils.NetworkUtil
import retrofit2.Response

class ApodRepository(
    val retrofitService: RetrofitService,
    val apodRoomDatabase: ApodRoomDatabase,
    val appContext: Context
) {
    private var response: Response<Apod>? = null
    private val API_KEY = "URBegYiXx26bvYXbMLYMGdbaiUgreK6q8FhGvvaQ"
    val apodDataLivedata = MutableLiveData<Apod>()

    suspend fun getApodByDate(date: String) {

        if (NetworkUtil.isInternetAvailable(appContext)) {
            response = retrofitService.getApodByDate(date, API_KEY)
            response!!.body()?.let {
                apodRoomDatabase.apodDao().insertApod(it)
                apodDataLivedata.postValue(response!!.body())
            }
        } else {
            val apodDbData = apodRoomDatabase.apodDao().getApodByDate(date)
            apodDataLivedata.postValue(apodDbData)
        }
    }

    suspend fun insertFavorite(favorites: Favorites) {
        apodRoomDatabase.apodDao().insertFavorite(favorites)
    }

    fun getFavoritesList() = apodRoomDatabase.apodDao().getAllFavorites()
}