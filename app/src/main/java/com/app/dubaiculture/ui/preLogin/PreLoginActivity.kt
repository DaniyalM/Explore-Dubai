package com.app.dubaiculture.ui.preLogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseActivity
import com.app.dubaiculture.ui.preLogin.login.LoginFragment
import com.app.dubaiculture.utils.AuthUtils.hideStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreLoginActivity : BaseActivity() {
    lateinit var btn: Button

    val loginFragment = LoginFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar(window)
        setContentView(R.layout.activity_pre_login)
        applicationEntry.activity=this
    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(resources.configuration)
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Check if the fragment is an instance of the right fragment
        LoginFragment().apply {
            handleIntent(intent)
        }

    }


}