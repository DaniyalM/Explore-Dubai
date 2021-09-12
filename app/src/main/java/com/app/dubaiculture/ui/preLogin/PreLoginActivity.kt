package com.app.dubaiculture.ui.preLogin

import android.os.Bundle
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

    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(resources.configuration)
    }


}