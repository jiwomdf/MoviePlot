package com.katilijiwo.movieplot.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.katilijiwo.movieplot.data.local.entity.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("DELETE FROM ms_movie WHERE id LIKE :movieID")
    suspend fun deleteMovie(movieID: Int)

    @Query("SELECT * FROM ms_movie ORDER BY timestamp DESC")
    fun getAllMovieSortedByDate(): LiveData<List<Movie>>

    @Query("SELECT * FROM ms_movie WHERE id = :id")
    fun getMovieByID(id: Int): LiveData<Movie?>
}