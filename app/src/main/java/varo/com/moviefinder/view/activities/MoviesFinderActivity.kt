package varo.com.moviefinder.view.activities

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.paginate.Paginate
import kotlinx.android.synthetic.main.activity_movies_list.count_label
import kotlinx.android.synthetic.main.activity_movies_list.progress_bar
import kotlinx.android.synthetic.main.activity_movies_list.recycler_view
import kotlinx.android.synthetic.main.activity_movies_list.sort_btn
import varo.com.domain.movie.Movie
import varo.com.domain.typedef.BaseState
import varo.com.moviefinder.R
import varo.com.moviefinder.view.adapters.LoadingAdapter
import varo.com.moviefinder.view.adapters.MovieListAdapter
import varo.com.moviefinder.view.adapters.MovieListAdapter.Listener
import varo.com.moviefinder.viewmodel.MovieFinderViewModel
import varo.com.moviefinder.viewmodel.MovieFinderViewModel.MoviesFinderState
import javax.inject.Inject


class MoviesFinderActivity : BaseActivity(), Listener {

    companion object {

        fun getStartingIntent(context: Context) = Intent(context, MoviesFinderActivity::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var moviesFinderViewModel: MovieFinderViewModel? = null
    private var moviesAdapter: MovieListAdapter? = null
    private var paginationAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent?.inject(this)

        setUpClickListeners()
        connectViewModel()
        setUpUI()

        moviesFinderViewModel?.fetchMovies()
    }

    override fun getLayoutId() = R.layout.activity_movies_list

    override fun getActivityTitle() = getString(R.string.app_name)

    override fun enableBackAction() = false

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorites, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.favorite -> {
                startActivity(FavoriteMoviesListActivity.getStartingIntent(this))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onMovieSelected(movie: Movie) {
        startActivity(MovieDetailsActivity.getStartingIntent(this, movie))
    }

    override fun onMovieFavoriteToggled(movie: Movie) {
        moviesFinderViewModel?.toggleMovieFavorite(movie)
    }

    private fun setUpClickListeners() {
        sort_btn.setOnClickListener { openSortOptionsDialog() }
    }

    private fun setUpUI() {
        moviesAdapter = MovieListAdapter(this, this, emptyList())

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MoviesFinderActivity)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(this@MoviesFinderActivity, LinearLayoutManager.VERTICAL
                )
            )
            adapter = moviesAdapter
        }
    }

    private fun addPagination() {
        paginationAdded = true
        Paginate.with(recycler_view, moviesFinderViewModel)
            .setLoadingTriggerThreshold(2)
            .addLoadingListItem(true)
            .setLoadingListItemCreator(LoadingAdapter())
            .build()
    }

    private fun connectViewModel() {
        moviesFinderViewModel = ViewModelProviders.of(this,
            viewModelFactory)[MovieFinderViewModel::class.java]
        moviesFinderViewModel?.moviesLiveData?.observe(this,
            Observer { it -> processIncomingState(it) })
    }

    private fun processIncomingState(state: MoviesFinderState?) {
        when (state?.state) {
            BaseState.INIT, BaseState.LOADING -> {
                setLoadingState()
            }
            BaseState.READY -> {
                if (null == state.movies || 0 == state.movies?.size) {
                    setNoDataState()
                } else {
                    displayMovies(state.movies!!, state.totalCount!!)
                }
            }
            BaseState.ERROR -> {
                setErrorState()
            }
        }
    }

    private fun setLoadingState() {
        count_label.text = getString(R.string.loading)
        progress_bar.visibility = View.VISIBLE
    }

    private fun setNoDataState() {
        count_label.text = getString(R.string.no_data)
        progress_bar.visibility = View.GONE
    }

    private fun setErrorState() {
        count_label.text = getString(R.string.error)
        progress_bar.visibility = View.GONE
    }

    private fun displayMovies(movies: List<Movie>, totalCount: Int) {
        if (!paginationAdded && !movies.isEmpty()) {
            addPagination()
        }

        count_label.text = resources.getQuantityString(R.plurals.movies_count, totalCount,
            totalCount)
        progress_bar.visibility = View.GONE
        moviesAdapter?.updateMoviesList(movies)
    }


    private fun openSortOptionsDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.sort))
            setItems(resources.getStringArray(R.array.sort_options),
                this@MoviesFinderActivity::onSortOptionSelected)
        }.create().show()
    }

    private fun onSortOptionSelected(dialogInterface: DialogInterface, which: Int) {
        when (which) {
            0 -> {
                moviesFinderViewModel?.sortMoviesByName()
            }
            1 -> {
                moviesFinderViewModel?.sortMoviesByRating()
            }
        }
    }
}