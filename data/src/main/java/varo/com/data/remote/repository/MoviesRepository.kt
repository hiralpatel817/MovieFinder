package varo.com.data.remote.repository

import io.reactivex.Observable
import varo.com.data.remote.client.Constants
import varo.com.data.remote.config.HostConfig
import varo.com.data.remote.service.MoviesService
import varo.com.domain.dto.MoviesResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val service: MoviesService) {

    fun getMoviesList(queryMap: HashMap<String, String>): Observable<MoviesResponse> {
        queryMap.apply {
            put(Constants.KEY_API, HostConfig.API_KEY)
        }
        return service.getMovies(queryMap)
    }
}