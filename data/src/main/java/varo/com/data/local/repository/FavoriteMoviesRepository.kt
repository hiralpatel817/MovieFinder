package varo.com.data.local.repository

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import varo.com.data.local.database.MoviesDatabase
import varo.com.domain.movie.Movie
import javax.inject.Inject

class FavoriteMoviesRepository @Inject constructor(private val database: MoviesDatabase) {

    fun getFavoriteMovies() = database.getMovieDao().getMovies()

    fun addMovieToFavorites(movie: Movie) {
        @Suppress("USUSED")
        Observable.just(database)
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                database.getMovieDao().insert(movie)
            }
    }

    fun deleteMovieFromFavorites(id: Int) {
        @Suppress("USUSED")
        Observable.just(database)
            .subscribeOn(Schedulers.io())
            .subscribe { db ->
                database.getMovieDao().deleteMovieWithId(id)
            }
    }
}