package com.example.nasaapod.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasaapod.database.entities.Favorites
import com.example.nasaapod.model.Apod
import com.example.nasaapod.networkManager.ApodRepository
import kotlinx.coroutines.*
import retrofit2.Response

class ApodViewModelFactory(private val date: String, private val repository: ApodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ApodViewModel(date, repository) as T
    }
}

class ApodViewModel(val date: String, val apodRepository: ApodRepository) : ViewModel() {

    var apodLivedata = MutableLiveData<Response<Apod>>()
    private lateinit var response : Response<Apod>

    init {
        getApodData()
    }

    private fun getApodData() =
        CoroutineScope(Dispatchers.IO).launch{
            response = apodRepository.getApodByDate(date, "URBegYiXx26bvYXbMLYMGdbaiUgreK6q8FhGvvaQ")
            withContext(Dispatchers.Main) {
                apodLivedata.value = response
            }
        }

    fun insertFavorite(favorites: Favorites) = CoroutineScope(Dispatchers.IO).launch {
        apodRepository.insertFavorite(favorites)
    }

    fun getFavoritesList() = apodRepository.getFavoritesList()
}