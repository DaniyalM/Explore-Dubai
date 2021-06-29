package com.app.dubaiculture.ui.postLogin.explore

import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import com.app.dubaiculture.utils.AuthUtils
import com.app.dubaiculture.utils.AuthUtils.hideStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreActivity : BaseAuthenticationActivity() {


    override fun baseOnCreate(savedInstanceState: Bundle?) {
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_generic)
        hideStatusBar(window)

//        setupViews(R.navigation.explore_navigation,binding.bottomNav)
//        BottomInit(binding.bottomNav, R.id.exploreFragment)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        recieveLogout()
    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(resources.configuration)

    }


}