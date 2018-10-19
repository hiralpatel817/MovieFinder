package varo.com.moviefinder.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import varo.com.moviefinder.di.viewmodel.ViewModelFactory
import varo.com.moviefinder.di.viewmodel.ViewModelKey
import varo.com.moviefinder.viewmodel.FavoriteMovieViewModel
import varo.com.moviefinder.viewmodel.MovieFinderViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        viewModelFactor: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieFinderViewModel::class)
    internal abstract fun provideMovideFinderViewModel(viewModel: MovieFinderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteMovieViewModel::class)
    internal abstract fun provideFavoriteMovieViewModel(
        viewModel: FavoriteMovieViewModel): ViewModel

}