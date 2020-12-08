package com.app.dubalculture.ui.preLogin

import android.os.Bundle
import com.app.dubalculture.R
import com.app.dubalculture.ui.base.BaseActivity
import com.app.dubalculture.utils.AuthUtils.hideStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreLoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar(window)
        setContentView(R.layout.activity_pre_login)
    }
}