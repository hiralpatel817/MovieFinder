package varo.com.moviefinder.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.paginate.Paginate
import varo.com.data.local.repository.FavoriteMoviesRepository
import varo.com.data.remote.client.Constants
import varo.com.data.remote.repository.MoviesRepository
import varo.com.domain.dto.MoviesResponse
import varo.com.domain.movie.Movie
import varo.com.domain.scheduler.SchedulerProvider
import varo.com.domain.typedef.BaseState
import varo.com.domain.typedef.MovieSortOptions
import java.util.HashMap
import javax.inject.Inject

class MovieFinderViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val moviesRepository: MoviesRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository
) : BaseViewModel(), Paginate.Callbacks {

    var moviesLiveData: MutableLiveData<MoviesFinderState> = MutableLiveData()
    private var moviesList = ArrayList<Movie>()
    private var totalResults = 0
    private var page = 0
    private var loadingInProgress = false
    private var hasLoadedAllItems = false
    private var sortOption: Int? = null

    init {
        moviesLiveData.value = MoviesFinderState(BaseState.INIT)
    }

    override fun onLoadMore() {
        if (!moviesList.isEmpty()) {
            fetchMovies()
        }
    }

    override fun isLoading(): Boolean {
        return loadingInProgress
    }

    override fun hasLoadedAllItems(): Boolean {
        return hasLoadedAllItems
    }

    fun fetchMovies() {
        loadingInProgress = true

        if (moviesList.isEmpty()) {
            moviesLiveData.value = MoviesFinderState(BaseState.LOADING)
        } else {
            moviesLiveData.value = MoviesFinderState(BaseState.FETCH)
        }

        val queryMap = HashMap<String, String>().apply {
            page++
            put(Constants.PAGE, page.toString())
        }

        addDisposible(moviesRepository.getMoviesList(queryMap)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribe(this::processMoviesResponse, this::processError))
    }

    fun sortMoviesByName() {
        sortOption = MovieSortOptions.NAME
        moviesList = ArrayList(sortArrayBySelectedSortOption(moviesList))
        moviesLiveData.value = MoviesFinderState(BaseState.READY, moviesList, totalResults)
    }

    fun sortMoviesByRating() {
        sortOption = MovieSortOptions.RATING
        moviesList = ArrayList(sortArrayBySelectedSortOption(moviesList))
        moviesLiveData.value = MoviesFinderState(BaseState.READY, moviesList, totalResults)
    }

    fun toggleMovieFavorite(movie: Movie) {
        favoriteMoviesRepository.addMovieToFavorites(movie)
    }

    private fun processMoviesResponse(moviesResponse: MoviesResponse) {
        loadingInProgress = false
        if (page == moviesResponse.totalPages) {
            hasLoadedAllItems = true
        }

        addMoviesToMoviesList(moviesResponse.results!!)
        totalResults = moviesResponse.totalResults!!

        moviesLiveData.value = MoviesFinderState(BaseState.READY, moviesList, totalResults)
    }

    private fun processError(throwable: Throwable?) {
        loadingInProgress = false
        moviesLiveData.value = MoviesFinderState(BaseState.ERROR, null, null, throwable)
    }

    private fun addMoviesToMoviesList(list: List<Movie>) {
        moviesList.addAll(list)
        moviesList = ArrayList(sortArrayBySelectedSortOption(moviesList))
    }

    private fun sortArrayBySelectedSortOption(list: List<Movie>): List<Movie> {
        when (sortOption) {
            MovieSortOptions.NAME -> {
                return list.sortedWith(kotlin.comparisons.compareBy { it.title })
            }
            MovieSortOptions.RATING -> {
                return list.sortedWith(kotlin.comparisons.compareBy { it.voteAverage })
            }
        }

        return list
    }

    data class MoviesFinderState(
        @BaseState.BaseState var state: Int,
        var movies: List<Movie>? = null,
        var totalCount: Int? = null,
        var throwable: Throwable? = null)
}