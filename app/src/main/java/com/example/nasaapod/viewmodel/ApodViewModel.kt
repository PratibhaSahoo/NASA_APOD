package com.example.nasaapod.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasaapod.database.entities.Favorites
import com.example.nasaapod.model.Apod
import com.example.nasaapod.networkManager.ApodRepository
import kotlinx.coroutines.*
import retrofit2.Response

class ApodViewModelFactory(private val date: String, private val repository: ApodRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ApodViewModel(date, repository) as T
    }
}

class ApodViewModel(val date: String, val apodRepository: ApodRepository) : ViewModel() {

    var apodLivedata = MutableLiveData<Response<Apod>>()
    private lateinit var response: Response<Apod>

    init {
        getApodData()
    }

    private fun getApodData() =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                response = apodRepository.getApodByDate(date)
                withContext(Dispatchers.Main) {
                    apodLivedata.value = response
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    fun insertFavorite(favorites: Favorites) = CoroutineScope(Dispatchers.IO).launch {
        try {
            apodRepository.insertFavorite(favorites)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getFavoritesList() = apodRepository.getFavoritesList()
}