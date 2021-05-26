package com.app.dubaiculture.ui.postLogin.events

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventActivity : BaseAuthenticationActivity() {

    override fun baseOnCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_generic)

        setupViews(R.navigation.events_navigation,binding.bottomNav)
        BottomInit(binding.bottomNav, R.id.eventsFragment)
    }

    override fun onResume() {
        super.onResume()
        adjustFontScale(resources.configuration)

    }

}