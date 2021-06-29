package com.app.dubaiculture.ui.postLogin


import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.setupWithNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import com.app.dubaiculture.utils.AuthUtils.hideStatusBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_post_login.*
import timber.log.Timber

@AndroidEntryPoint
class PostLoginActivity : BaseAuthenticationActivity(), NavController.OnDestinationChangedListener {

    private val mainViewModel: MainViewModel by viewModels()

    private val navHolding: Int = R.id.nav_host_fragment


    override fun baseOnCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_post_login)
        applicationEntry.auth.locale = getCurrentLanguage().language
        hideStatusBar(window)
        subscribeUiEvents(mainViewModel)
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


}



