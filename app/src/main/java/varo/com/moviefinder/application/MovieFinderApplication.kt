package varo.com.moviefinder.application

import android.app.Application
import varo.com.moviefinder.di.component.ApplicationComponent
import varo.com.moviefinder.di.component.DaggerApplicationComponent
import varo.com.moviefinder.di.modules.ApplicationModule

class MovieFinderApplication : Application() {

    var applicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        initRestClient()
        initInjection()
    }

    private fun initRestClient() {
        // TODO - If there are RestClient configurations that need to happen, do them here
    }

    private fun initInjection() {
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(
            ApplicationModule(this)).build()
    }
}