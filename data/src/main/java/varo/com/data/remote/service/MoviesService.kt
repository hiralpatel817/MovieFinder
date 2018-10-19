package varo.com.data.remote.service

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import varo.com.domain.dto.MoviesResponse

interface MoviesService {

    @GET("movie/now_playing")
    fun getMovies(@QueryMap queryMap: Map<String, String>): Observable<MoviesResponse>
}