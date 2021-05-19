package com.app.dubaiculture.ui.postLogin.explore

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.ActivityGenericBinding
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import com.app.dubaiculture.ui.postLogin.attractions.AttractionActivity
import com.app.dubaiculture.ui.postLogin.attractions.detail.login.PostLoginFragment
import com.app.dubaiculture.ui.postLogin.events.EventActivity
import com.app.dubaiculture.ui.postLogin.more.MoreActivity
import com.app.dubaiculture.utils.startNewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_post_login.*

@AndroidEntryPoint
class ExploreActivity : BaseAuthenticationActivity() {


    override fun baseOnCreate(savedInstanceState: Bundle?) {

        binding= DataBindingUtil.setContentView(this, R.layout.activity_generic)
        setupViews(R.navigation.explore_navigation)
        BottomInit(binding.bottomNav,R.id.exploreFragment)

    }





}