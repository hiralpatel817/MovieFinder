package varo.com.moviefinder.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import varo.com.moviefinder.R
import varo.com.moviefinder.application.MovieFinderApplication

abstract class BaseActivity : AppCompatActivity() {

    private val baseApplication get() = application as MovieFinderApplication
    protected val applicationComponent get() = baseApplication.applicationComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent?.inject(this)
        setContentView(getLayoutId())
        initToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    abstract fun getLayoutId(): Int

    abstract fun getActivityTitle(): String

    abstract fun enableBackAction(): Boolean

    private fun initToolbar() {
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        if (null != toolbar) {
            setSupportActionBar(toolbar)
        }
        supportActionBar?.title = getActivityTitle()
        supportActionBar?.setDisplayHomeAsUpEnabled(enableBackAction())
    }

    fun overrideToolbarTitle(title: String) {
        supportActionBar?.title = title
    }
}