package com.daniyal.dubalculture.ui.preLogin

import android.os.Bundle
import com.daniyal.dubalculture.R
import com.daniyal.dubalculture.ui.base.BaseActivity
import com.daniyal.dubalculture.utils.AuthUtils.hideStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreLoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar(window)
        setContentView(R.layout.activity_pre_login)
    }
}