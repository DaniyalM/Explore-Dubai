package com.app.dubaiculture.ui.preLogin

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
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
//        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        applicationExitDialog()

    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(resources.configuration)
    }

    private fun applicationExitDialog() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    showAlert(
                        message = getString(R.string.error_msg),
                        textPositive = getString(R.string.okay),
                        textNegative = getString(R.string.cancel),
                        actionPositive = {
                            finish()
                        }
                    )
                }
            }
      onBackPressedDispatcher.addCallback(this, callback)
    }

}