package com.app.dubalculture.ui.base

import android.os.Bundle
import com.app.dubalculture.ui.preLogin.PreLoginActivity
import com.app.dubalculture.utils.killSessionAndStartNewActivity

abstract class BaseAuthenticationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLoginStatus()
        baseOnCreate(savedInstanceState)
    }

    private fun checkLoginStatus() {

        //IF User has logged In Proceed with Activity Other Wise Navigate User to Login Screen
//        applicationEntry.auth.isLoggedIn = false
        //We will get the User Session From DataStore to check If its LoggedIn Or not
         if(!applicationEntry.auth.isLoggedIn){
            killSessionAndStartNewActivity(PreLoginActivity::class.java)
        }





    }

    protected abstract fun baseOnCreate(savedInstanceState: Bundle?)
}