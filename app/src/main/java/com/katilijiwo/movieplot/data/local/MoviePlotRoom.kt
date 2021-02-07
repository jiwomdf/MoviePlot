package com.katilijiwo.movieplot.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.katilijiwo.movieplot.data.Converters
import com.katilijiwo.movieplot.data.local.dao.MovieDao
import com.katilijiwo.movieplot.data.local.entity.Movie

@Database(
    entities = [Movie::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class MoviePlotRoom: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}