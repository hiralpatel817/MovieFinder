package varo.com.moviefinder.viewmodel

import varo.com.data.local.repository.FavoriteMoviesRepository
import varo.com.domain.movie.Movie
import javax.inject.Inject

class FavoriteMovieViewModel @Inject constructor(
    private val favoriteMoviesRepository: FavoriteMoviesRepository
) : BaseViewModel() {

    var favoriteMoviesLiveData = favoriteMoviesRepository.getFavoriteMovies()

    fun removeFromFavorites(movie: Movie) {
        favoriteMoviesRepository.deleteMovieFromFavorites(movie.id!!)
    }
}