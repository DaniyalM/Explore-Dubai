package com.dubaiculture.ui.preLogin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.dubaiculture.R
import com.dubaiculture.ui.base.BaseActivity
import com.dubaiculture.ui.postLogin.PostLoginActivity
import com.dubaiculture.ui.preLogin.login.LoginFragment
import com.dubaiculture.utils.AuthUtils.hideStatusBar
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.killSessionAndStartNewActivity
import dagger.hilt.android.AndroidEntryPoint
import om.dubaiculture.ui.navGraphActivity.NavGraphActivity
import timber.log.Timber


@AndroidEntryPoint
class PreLoginActivity : BaseActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar(window)
        setContentView(R.layout.activity_pre_login)
        logDeepLinkUri()
        navigate()

    }

    private fun navigate(){

        intent?.let {
            val handle = it.getBooleanExtra(Constants.NavBundles.HANDLE_PUSH, false)
            Timber.e(""+handle)
            if (handle) {
                killSessionAndStartNewActivity(PostLoginActivity::class.java)
                val intent = Intent(
                    this,
                    NavGraphActivity::class.java
                )
                intent.putExtras(it)
                startActivity(intent)
            }
        }
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
    private fun logDeepLinkUri() {
        val data: Uri? = this.intent.data
        if (data != null && data.isHierarchical) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val uri = this.intent.dataString
            Log.e("MyApp", "Deep link clicked $uri")
        }
    }

}