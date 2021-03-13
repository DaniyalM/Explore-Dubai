package com.app.dubaiculture.ui.postLogin

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

//,
//OnStreetViewPanoramaReadyCallback
@AndroidEntryPoint
class PostLoginActivity : BaseAuthenticationActivity() {

    private val mainViewModel: MainViewModel by viewModels()
//    private lateinit var streetViewPanoramaFragment: SupportStreetViewPanoramaFragment

//    private fun showStreetView() {
//        streetViewPanoramaFragment =
//            supportFragmentManager
//                .findFragmentById(R.id.streetViewFragment) as SupportStreetViewPanoramaFragment
//        streetViewPanoramaFragment.let {
//            it.getStreetViewPanoramaAsync(this)
//        }
//
//    }

    override fun baseOnCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_post_login)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        subscribeUiEvents(mainViewModel)

    }

    override fun onStart() {
        super.onStart()
        Timber.e("Start")
    }
    override fun onRestart() {
        super.onRestart()
        Timber.e("Restart")
    }
    override fun onResume() {
        super.onResume()
        adjustFontScale(getResources().getConfiguration());

    }


    open fun adjustFontScale(configuration: Configuration) {
        if (configuration.fontScale > 1.30) {
            configuration.fontScale = 1.30f
            val metrics = resources.displayMetrics
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager?
            wm!!.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            this.getResources().updateConfiguration(configuration, metrics)
        }
    }

}