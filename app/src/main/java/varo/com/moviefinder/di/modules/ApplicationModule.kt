package varo.com.moviefinder.di.modules

import dagger.Module
import dagger.Provides
import varo.com.moviefinder.application.MovieFinderApplication
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MovieFinderApplication) {

    @Provides
    @Singleton
    internal fun providesApplication() = application

    @Provides
    @Singleton
    internal fun providesApplicationContext() = application.applicationContext
}