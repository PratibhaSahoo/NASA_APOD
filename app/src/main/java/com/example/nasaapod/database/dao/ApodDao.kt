package com.example.nasaapod.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.nasaapod.database.entities.Favorites
import com.example.nasaapod.model.Apod
import retrofit2.Response

@Dao
interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(vararg favorites: Favorites)

    @Query("select * from favorites")
    fun getAllFavorites() : LiveData<List<Favorites>>

    @Delete
    suspend fun deleteFavorite(favorites: Favorites)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApod(vararg apod: Apod)

    @Query("select * from apodtable WHERE date = :date")
    fun getApodByDate(date: String) : Apod

}