package com.app.dubaiculture.ui.postLogin.more

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.ActivityGenericBinding
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_post_login.*
@AndroidEntryPoint
class MoreActivity: BaseAuthenticationActivity() {
    override fun baseOnCreate(savedInstanceState: Bundle?) {
        binding= DataBindingUtil.setContentView(this, R.layout.activity_generic)

        setupViews(R.navigation.more_navigation)
        BottomInit(binding.bottomNav,R.id.moreFragment)
    }

}