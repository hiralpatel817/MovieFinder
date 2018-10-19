package varo.com.moviefinder.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paginate.recycler.LoadingListItemCreator
import varo.com.moviefinder.R.layout

class LoadingAdapter : LoadingListItemCreator {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent?.context).inflate(layout.row_loading, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}