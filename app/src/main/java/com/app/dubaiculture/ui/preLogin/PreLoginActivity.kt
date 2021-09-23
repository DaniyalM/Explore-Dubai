package com.app.dubaiculture.ui.preLogin

import ae.sdg.libraryuaepass.UAEPassAccessCodeCallback
import ae.sdg.libraryuaepass.UAEPassController.getAccessCode
import ae.sdg.libraryuaepass.business.authentication.model.UAEPassAccessTokenRequestModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseActivity
import com.app.dubaiculture.ui.preLogin.login.LoginFragment
import com.app.dubaiculture.utils.AuthUtils.hideStatusBar
import com.app.dubaiculture.utils.uaePassUtils.UAEPassRequestModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreLoginActivity : BaseActivity() {
    lateinit var btn : Button

    val loginFragment = LoginFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar(window)
        setContentView(R.layout.activity_pre_login)
//        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//        btn = findViewById(R.id.btn)
//        btn.setOnClickListener {
//            getCode()
//
//        }
    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(resources.configuration)
    }
    private fun getCode() {
        val uaePassRequestModels = UAEPassRequestModels()
        val requestModel: UAEPassAccessTokenRequestModel? = uaePassRequestModels.getAuthenticationRequestModel(
            this
        )
        requestModel?.let {
            getAccessCode(this, it, object : UAEPassAccessCodeCallback {
                override fun getAccessCode(code: String?, error: String?) {
                    if (error != null) {
                        Toast.makeText(
                            this@PreLoginActivity,
                            "Error while getting access token",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@PreLoginActivity,
                            "Access Code Received",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Check if the fragment is an instance of the right fragment
        // Check if the fragment is an instance of the right fragment
        if (loginFragment is LoginFragment) {
            val my: LoginFragment = loginFragment as LoginFragment
            // Pass intent or its data to the fragment's method
            my.handleIntent(intent)
        }
    }


}