package com.app.dubaiculture.ui.postLogin

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_post_login.*
import timber.log.Timber

//,
//OnStreetViewPanoramaReadyCallback
@AndroidEntryPoint
class PostLoginActivity : BaseAuthenticationActivity() {

    lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels()


    override fun baseOnCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_post_login)
        applicationEntry.auth.locale = getCurrentLanguage().language
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        subscribeUiEvents(mainViewModel)
        setupViews()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.threeSixtyFragment -> {
                    bottomNav.visibility = View.GONE
                }
                R.id.registrationSuccessFragment2 -> {
                    bottomNav.visibility = View.GONE
                }
                else -> {
                    bottomNav.visibility = View.VISIBLE

                }
            }


        }

    }

    private fun setupViews() {
        val fragmentContainer = findViewById<View>(R.id.nav_host_fragment)
        navController = Navigation.findNavController(fragmentContainer)

        bottomNav.setupWithNavController(navController)
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
        adjustFontScale(resources.configuration)

    }


    private fun adjustFontScale(configuration: Configuration) {
        if (configuration.fontScale > 1.30) {
            configuration.fontScale = 1.30f
            val metrics = resources.displayMetrics
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager?
            wm!!.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            this.resources.updateConfiguration(configuration, metrics)
        }
    }


}



