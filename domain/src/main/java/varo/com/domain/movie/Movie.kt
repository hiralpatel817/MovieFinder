package varo.com.domain.movie

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie(
    @SerializedName("id") @PrimaryKey var id: Int? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("backdrop_path") var backdrop_path: String? = null,
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null
)