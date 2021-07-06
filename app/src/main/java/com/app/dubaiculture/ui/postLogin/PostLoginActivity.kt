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
import kotlinx.android.synthetic.main.activity_post_login.*
import timber.log.Timber

@AndroidEntryPoint
class PostLoginActivity : BaseAuthenticationActivity(), NavController.OnDestinationChangedListener {


    private val navHolding: Int = R.id.nav_host_fragment


    override fun baseOnCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_post_login)
        applicationEntry.auth.locale = getCurrentLanguage().language
        hideStatusBar(window)
        getNavControllerFun(navHolding).addOnDestinationChangedListener(this)

        recieveLogout()


    }



    override fun onDestroy() {
        getNavControllerFun(navHolding).removeOnDestinationChangedListener(this)
        super.onDestroy()
    }


    override fun onDestinationChanged(
            controller: NavController,
            destination: NavDestination,
            arguments: Bundle?
    ) {
//        currentFocus?.hi()
    }

    override fun onStart() {
        super.onStart()
        Timber.e("Start")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.e("Restart")
    }

    override fun onResume() {
        super.onResume()
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



