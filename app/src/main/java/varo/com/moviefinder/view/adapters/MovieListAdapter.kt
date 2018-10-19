package varo.com.moviefinder.view.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_movie_item.view.container
import kotlinx.android.synthetic.main.row_movie_item.view.favorite
import kotlinx.android.synthetic.main.row_movie_item.view.poster
import kotlinx.android.synthetic.main.row_movie_item.view.release_date
import kotlinx.android.synthetic.main.row_movie_item.view.title
import kotlinx.android.synthetic.main.row_movie_item.view.vote_average
import kotlinx.android.synthetic.main.row_movie_item.view.vote_count
import varo.com.domain.movie.Movie
import varo.com.moviefinder.R
import varo.com.moviefinder.util.GlideUtil
import varo.com.moviefinder.util.ImageSizingUtil

class MovieListAdapter(
    val context: Context,
    val listener: Listener,
    var items: List<Movie>
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    fun updateMoviesList(movies: List<Movie>) {
        items = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.row_movie_item, parent, false))
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val movie = items[position]
        view.container.setOnClickListener { listener.onMovieSelected(items[position]) }
        view.favorite.setOnClickListener { listener.onMovieFavoriteToggled(items[position]) }
        view.title.text = movie.title
        view.releaseDate.text = movie.releaseDate
        view.voteCount.text = String.format(context.getString(R.string.total_votes_display),
            movie.voteCount.toString())
        view.voteAverage.text = String.format(context.getString(R.string.average_vote_display),
            movie.voteAverage.toString())

        if (null != movie.posterPath) {
            GlideUtil.loadFromPath(context, ImageSizingUtil.getPathForThumbnail(movie.posterPath!!),
                view.poster)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container = view.container
        val title = view.title
        val releaseDate = view.release_date
        val voteCount = view.vote_count
        val voteAverage = view.vote_average
        val poster = view.poster
        val favorite = view.favorite
    }

    interface Listener {

        fun onMovieSelected(movie: Movie)

        fun onMovieFavoriteToggled(movie: Movie)
    }
}