package varo.com.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import varo.com.domain.movie.Movie

@Dao
abstract class MovieDao : BaseDao<Movie> {

    @Query("SELECT * FROM movies")
    @Transaction
    abstract fun getMovies(): LiveData<List<Movie>>

    @Query("DELETE FROM movies WHERE id = :id")
    @Transaction
    abstract fun deleteMovieWithId(id: Int)
}