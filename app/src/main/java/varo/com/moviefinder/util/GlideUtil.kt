package varo.com.moviefinder.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888
import com.bumptech.glide.load.engine.DiskCacheStrategy.NONE

class GlideUtil {

    companion object {

        fun loadFromPath(context: Context, url: String, view: ImageView) {
            Glide.with(context)
                .load(url)
                .asBitmap()
                .format(PREFER_ARGB_8888)
                .diskCacheStrategy(NONE)
                .into(view)
        }
    }
}