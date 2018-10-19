package varo.com.moviefinder.view.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_favorite_movies_list.count_label
import kotlinx.android.synthetic.main.activity_favorite_movies_list.progress_bar
import kotlinx.android.synthetic.main.activity_favorite_movies_list.recycler_view
import varo.com.domain.movie.Movie
import varo.com.moviefinder.R
import varo.com.moviefinder.view.adapters.MovieListAdapter
import varo.com.moviefinder.view.adapters.MovieListAdapter.Listener
import varo.com.moviefinder.viewmodel.FavoriteMovieViewModel
import javax.inject.Inject

class FavoriteMoviesListActivity : BaseActivity(), Listener {

    companion object {

        fun getStartingIntent(context: Context) = Intent(context,
            FavoriteMoviesListActivity::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var favoriteMovieViewModel: FavoriteMovieViewModel? = null
    private var moviesAdapter: MovieListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent?.inject(this)

        setUpUI()
        setLoadingState()
        connectViewModel()
    }

    override fun getLayoutId() = R.layout.activity_favorite_movies_list

    override fun getActivityTitle() = getString(R.string.favorites)

    override fun enableBackAction() = true

    override fun onMovieSelected(movie: Movie) {
        startActivity(MovieDetailsActivity.getStartingIntent(this, movie))
    }

    override fun onMovieFavoriteToggled(movie: Movie) {
        favoriteMovieViewModel?.removeFromFavorites(movie)
    }

    private fun setUpUI() {
        moviesAdapter = MovieListAdapter(this, this, emptyList())

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@FavoriteMoviesListActivity)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(this@FavoriteMoviesListActivity, LinearLayoutManager.VERTICAL
                )
            )
            adapter = moviesAdapter
        }
    }

    private fun connectViewModel() {
        favoriteMovieViewModel = ViewModelProviders.of(this,
            viewModelFactory)[FavoriteMovieViewModel::class.java]

        favoriteMovieViewModel?.favoriteMoviesLiveData?.observe(this,
            Observer { it -> processIncomingState(it) })
    }

    private fun processIncomingState(movies: List<Movie>?) {
        if (null == movies || movies.isEmpty()) {
            setNoDataState()
        } else {
            displayMovies(movies, movies.size)
        }
    }

    private fun setLoadingState() {
        count_label.text = getString(R.string.loading)
        progress_bar.visibility = View.VISIBLE
    }

    private fun setNoDataState() {
        count_label.text = getString(R.string.no_data)
        progress_bar.visibility = View.GONE
        moviesAdapter?.updateMoviesList(emptyList())
    }

    private fun displayMovies(movies: List<Movie>, totalCount: Int) {
        count_label.text = resources.getQuantityString(R.plurals.movies_count, totalCount,
            totalCount)
        progress_bar.visibility = View.GONE
        moviesAdapter?.updateMoviesList(movies)
    }
}