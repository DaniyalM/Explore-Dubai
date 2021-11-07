package com.dubaiculture.ui.postLogin


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.dubaiculture.R
import com.dubaiculture.ui.base.BaseAuthenticationActivity
import com.dubaiculture.ui.postLogin.login.PostLoginFragment
import com.dubaiculture.ui.postLogin.more.viewmodel.MoreSharedViewModel
import com.dubaiculture.utils.AuthUtils.hideStatusBar
import com.dubaiculture.utils.firebase.subscribeToTopic
import com.dubaiculture.utils.firebase.unSubscribeFromTopic
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostLoginActivity : BaseAuthenticationActivity() {
    private val navHolding: Int = R.id.nav_host_fragment
    private val moreSharedViewModel: MoreSharedViewModel by viewModels()

    override fun baseOnCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_post_login)
        hideStatusBar(window)
        getNavControllerFun(navHolding)
        recieveLogout()
        subscribeToObservable()
        getCurrentLanguage().language.let {
            if (it.equals("en")) {
                unSubscribeFromTopic("AndroidBroadcast_ar")
            } else {
                unSubscribeFromTopic("AndroidBroadcast_en")
            }
            val id = it + "_" + "${applicationEntry.auth.user?.userId}"
            subscribeToTopic(topic = "AndroidBroadcast_$it")
            subscribeToTopic(topic = "AndroidBroadcast_$id")


        }


    }

    private fun subscribeToObservable() {
        moreSharedViewModel.isLogged.observe(this) {
            it?.getContentIfNotHandled()?.let {
                if (it) {
                    initiateLogout()
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        SystemRequirementsChecker.checkWithDefaultDialogs(this)
        adjustFontScale(resources.configuration)

    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Check if the fragment is an instance of the right fragment

        navController.handleDeepLink(intent)
        PostLoginFragment().apply {
            handleIntent(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(reciever)
    }


}



