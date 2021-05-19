package com.app.dubaiculture.ui.postLogin.explore

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreActivity : BaseAuthenticationActivity() {


    override fun baseOnCreate(savedInstanceState: Bundle?) {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_generic)
        setupViews(R.navigation.explore_navigation,binding.bottomNav)
        BottomInit(binding.bottomNav, R.id.exploreFragment)

    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(resources.configuration)

    }


}