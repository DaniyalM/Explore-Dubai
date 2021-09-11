package com.app.dubaiculture.ui.postLogin


import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.setupWithNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import com.app.dubaiculture.ui.postLogin.more.services.MoreService
import com.app.dubaiculture.utils.AuthUtils.hideStatusBar
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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


}



