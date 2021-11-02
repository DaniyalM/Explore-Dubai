package com.app.dubaiculture.ui.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.CheckBox
import com.app.dubaiculture.data.repository.event.remote.request.AddToFavouriteRequest
import com.app.dubaiculture.ui.preLogin.PreLoginActivity
import com.app.dubaiculture.utils.firebase.unSubscribeFromTopic
import com.app.dubaiculture.utils.killSessionAndStartNewActivity


abstract class BaseAuthenticationActivity : BaseActivity() {

//    protected lateinit var binding: ActivityGenericBinding

    protected val reciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            applicationEntry.auth.user = null
            applicationEntry.auth.isLoggedIn = false
            checkLoginStatus()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        applicationEntry.auth.locale = getCurrentLanguage().language

        checkLoginStatus()

        baseOnCreate(savedInstanceState)
    }


    protected fun checkLoginStatus() {

        //IF User has logged In Proceed with Activity Other Wise Navigate User to Login Screen
        //We will get the User Session From DataStore to check If its LoggedIn Or not
        if (!applicationEntry.auth.isLoggedIn) {
            killSessionAndStartNewActivity(PreLoginActivity::class.java)
        }


    }

    protected abstract fun baseOnCreate(savedInstanceState: Bundle?)
    fun favouriteClick(
        checkbox: CheckBox,
        isFav: Boolean,
        nav: Int,
        itemId: String,
        baseViewModel: BaseViewModel,
        type: Int = 2,
    ) {
        checkBox = checkbox
        if (applicationEntry.auth.isGuest) {
            navigate(nav)
        } else {
            if (!isFav) {
                applicationEntry.auth.user?.let {
                    baseViewModel.addToFavourites(
                        AddToFavouriteRequest(
                            userId = applicationEntry.auth.user?.userId,
                            itemId = itemId,
                            type = type
                        )
                    )
                }
            } else {
                applicationEntry.auth.user?.let {
                    baseViewModel.addToFavourites(
                        AddToFavouriteRequest(
                            userId = applicationEntry.auth.user?.userId,
                            itemId = itemId,
                            type = type
                        )
                    )
                }
            }

        }
    }

    fun initiateLogout() {
        val broadcastIntent = Intent()
        broadcastIntent.action = "com.package.ACTION_LOGOUT"
        sendBroadcast(broadcastIntent)
    }

    fun recieveLogout() {
        IntentFilter().apply {
            addAction("com.package.ACTION_LOGOUT")
            registerReceiver(reciever, this)
        }
        getCurrentLanguage().language.let {
            unSubscribeFromTopic("AndroidBroadcast_$it")
        }
    }


}