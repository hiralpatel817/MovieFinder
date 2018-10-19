package varo.com.domain.dto

import com.google.gson.annotations.SerializedName
import varo.com.domain.common.BaseListResponse
import varo.com.domain.movie.Movie

data class MoviesResponse(@SerializedName(
    "results") val results: ArrayList<Movie>? = null) : BaseListResponse()