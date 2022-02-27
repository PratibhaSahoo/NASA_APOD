package com.example.nasaapod.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorites(
    @PrimaryKey var date: String,
    @ColumnInfo var explanation: String?,
    @ColumnInfo var hdurl: String?,
    @ColumnInfo var title: String?
)
