package com.app.dubaiculture.ui.postLogin.attractions

import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import com.app.dubaiculture.utils.AuthUtils.hideStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttractionActivity : BaseAuthenticationActivity() {
    override fun baseOnCreate(savedInstanceState: Bundle?) {
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_generic)
        hideStatusBar(window)
        setupViews(R.navigation.attraction_navigation,binding.bottomNav)
        BottomInit(binding.bottomNav, R.id.attractionsFragment)

    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(resources.configuration)
    }
}