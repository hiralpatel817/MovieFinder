package varo.com.moviefinder.di.modules

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import varo.com.data.remote.service.MoviesService
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    internal fun provideMovieService(retrofit: Retrofit) =
        retrofit.create(MoviesService::class.java)
}