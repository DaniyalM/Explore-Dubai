package com.app.dubaiculture.ui.postLogin.more

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import com.app.dubaiculture.ui.postLogin.more.services.MoreService
import com.app.dubaiculture.utils.AuthUtils.hideStatusBar
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreActivity : BaseAuthenticationActivity() {
    override fun baseOnCreate(savedInstanceState: Bundle?) {
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_generic)
        hideStatusBar(window)
//        setupViews(R.navigation.more_navigation, binding.bottomNav)
//        BottomInit(binding.bottomNav, R.id.moreFragment)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        recieveLogout()

    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(resources.configuration)

    }


}