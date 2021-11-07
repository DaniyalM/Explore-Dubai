package com.dubaiculture.ui.preLogin

import android.content.Intent
import android.os.Bundle
import com.dubaiculture.R
import com.dubaiculture.ui.base.BaseActivity
import com.dubaiculture.ui.preLogin.login.LoginFragment
import com.dubaiculture.utils.AuthUtils.hideStatusBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PreLoginActivity : BaseActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar(window)
        setContentView(R.layout.activity_pre_login)

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