package varo.com.moviefinder.di.component

import dagger.Component
import varo.com.moviefinder.di.modules.ApplicationModule
import varo.com.moviefinder.di.modules.NetworkModule
import varo.com.moviefinder.di.modules.RepositoryModule
import varo.com.moviefinder.di.modules.ServiceModule
import varo.com.moviefinder.di.modules.ViewModelModule
import varo.com.moviefinder.view.activities.BaseActivity
import varo.com.moviefinder.view.activities.FavoriteMoviesListActivity
import varo.com.moviefinder.view.activities.MovieDetailsActivity
import varo.com.moviefinder.view.activities.MoviesFinderActivity
import varo.com.moviefinder.view.activities.SplashActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        ServiceModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ])
interface ApplicationComponent {

    fun inject(activity: BaseActivity)

    fun inject(activity: SplashActivity)

    fun inject(activity: MoviesFinderActivity)

    fun inject(activity: FavoriteMoviesListActivity)

    fun inject(activity: MovieDetailsActivity)
}