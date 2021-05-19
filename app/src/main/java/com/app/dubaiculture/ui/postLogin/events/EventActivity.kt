package com.app.dubaiculture.ui.postLogin.events

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_post_login.*

@AndroidEntryPoint
class EventActivity: BaseAuthenticationActivity() {

    override fun baseOnCreate(savedInstanceState: Bundle?) {
        binding= DataBindingUtil.setContentView(this, R.layout.activity_generic)

        setupViews(R.navigation.events_navigation)
        BottomInit(binding.bottomNav,R.id.eventsFragment)
    }



}