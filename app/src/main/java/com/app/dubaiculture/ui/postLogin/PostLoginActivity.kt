package com.app.dubaiculture.ui.postLogin


import android.content.Intent
import android.os.Bundle
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import com.app.dubaiculture.ui.postLogin.login.PostLoginFragment
import com.app.dubaiculture.ui.postLogin.more.services.MoreService
import com.app.dubaiculture.ui.preLogin.login.LoginFragment
import com.app.dubaiculture.utils.AuthUtils.hideStatusBar
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostLoginActivity : BaseAuthenticationActivity() {
    private val navHolding: Int = R.id.nav_host_fragment


    override fun baseOnCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_post_login)
        applicationEntry.auth.locale = getCurrentLanguage().language
        hideStatusBar(window)
        getNavControllerFun(navHolding)
        recieveLogout()


    }


    override fun onResume() {
        super.onResume()
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        applicationEntry.auth.locale = getCurrentLanguage().language
        adjustFontScale(resources.configuration)

    }

    @Subscribe
    fun initiateLogout(service: MoreService) {
        when (service) {
            is MoreService.Logout -> {
                applicationEntry.auth.isLoggedIn = false
                // Subscribe broadcast from here.  when user logout click from moreFragment. then broadcast fire
                initiateLogout()
                checkLoginStatus()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Check if the fragment is an instance of the right fragment
       PostLoginFragment().apply {
           handleIntent(intent)
       }
    }


}



