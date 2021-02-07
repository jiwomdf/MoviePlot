package com.katilijiwo.movieplot.data.local.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ms_movie")
data class Movie(
    @PrimaryKey
    var id: Int,
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    val title: String,
    val tagline: String,
    val imdbId: String,
    val vote: String,
    val desc: String
)