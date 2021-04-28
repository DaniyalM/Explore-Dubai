package com.app.dubaiculture.ui.preLogin

import android.content.res.Configuration
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseActivity
import com.app.dubaiculture.utils.AuthUtils.hideStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreLoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar(window)
        setContentView(R.layout.activity_pre_login)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(getResources().getConfiguration())
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