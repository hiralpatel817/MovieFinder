package varo.com.moviefinder.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import varo.com.data.local.database.MoviesDatabase
import varo.com.data.local.repository.FavoriteMoviesRepository
import varo.com.data.remote.repository.MoviesRepository
import varo.com.data.remote.service.MoviesService
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides @Singleton
    fun provideMoviesRepository(service: MoviesService) = MoviesRepository(service)

    @Provides @Singleton
    fun provideFavoriteMoviesRepository(context: Context) =
        FavoriteMoviesRepository(MoviesDatabase.getInstance(context))
}