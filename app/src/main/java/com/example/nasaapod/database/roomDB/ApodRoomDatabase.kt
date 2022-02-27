package com.example.nasaapod.database.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nasaapod.database.dao.ApodDao
import com.example.nasaapod.database.entities.ApodEntity
import com.example.nasaapod.database.entities.Favorites
import com.example.nasaapod.model.Apod

@Database(entities = [Favorites::class, Apod::class], version = 1)
abstract class ApodRoomDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var apodDBInstance: ApodRoomDatabase? = null

        fun getDBInstance(context: Context): ApodRoomDatabase {
            if (apodDBInstance == null) {
                synchronized(this) {
                    apodDBInstance = Room.databaseBuilder(
                        context.applicationContext,
                        ApodRoomDatabase::class.java,
                        "ApodDatabase"
                    )
                        .build()
                }
            }
            return apodDBInstance!!
        }
    }

    abstract fun apodDao(): ApodDao
}