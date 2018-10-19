package varo.com.moviefinder.view.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import varo.com.moviefinder.R


class SplashActivity : BaseActivity() {

    private val mDelay = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent?.inject(this)

        openMovieFinderActivity()
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun getActivityTitle() = ""

    override fun enableBackAction() = false

    private fun openMovieFinderActivity() {
        val launch = Runnable {
            startActivity(
                MoviesFinderActivity.getStartingIntent(
                    this)).also {
                finish()
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out)
            }
        }

        Handler(Looper.getMainLooper()).postDelayed(launch, mDelay)
    }
}