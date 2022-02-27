package com.example.nasaapod.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ApodTable")
data class Apod(
    @PrimaryKey val date: String,
    val explanation: String?,
    val hdurl: String?,
    val media_type: String?,
    val service_version: String?,
    val title: String?,
    val url: String?
)