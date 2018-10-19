package varo.com.data.local.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import varo.com.data.local.dao.MovieDao
import varo.com.data.local.utils.ioThread
import varo.com.domain.movie.Movie

@Database(entities = [
    Movie::class
], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: MoviesDatabase? = null
        private const val DATABASE_NAME = "movies_database"

        fun getInstance(context: Context): MoviesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: createDatabase(context).also {
                    INSTANCE = it
                }
            }

        fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MoviesDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            // TODO - Init DB on start up
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun getMovieDao(): MovieDao
}