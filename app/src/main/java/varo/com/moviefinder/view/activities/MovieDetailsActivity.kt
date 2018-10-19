package varo.com.moviefinder.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_details.banner
import kotlinx.android.synthetic.main.activity_movie_details.description
import varo.com.domain.movie.Movie
import varo.com.moviefinder.R
import varo.com.moviefinder.util.GlideUtil
import varo.com.moviefinder.util.ImageSizingUtil

class MovieDetailsActivity : BaseActivity() {

    companion object {

        val INCOMING_MOVIE = "INCOMING_MOVIE"

        fun getStartingIntent(context: Context, movie: Movie) = Intent(context,
            MovieDetailsActivity::class.java).apply {
            putExtra(INCOMING_MOVIE, Gson().toJson(movie))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent?.inject(this)
        getIncomingData()
    }

    override fun getLayoutId() = R.layout.activity_movie_details

    override fun getActivityTitle() = ""

    override fun enableBackAction() = true

    private fun getIncomingData() {
        val movie = Gson().fromJson(intent?.getStringExtra(INCOMING_MOVIE), Movie::class.java)
        overrideToolbarTitle(movie.title!!)

        if (null != movie.backdrop_path) {
            GlideUtil.loadFromPath(this, ImageSizingUtil.getPathForBanner(movie.backdrop_path!!),
                banner)
        }

        description.text = movie.overview
    }
}