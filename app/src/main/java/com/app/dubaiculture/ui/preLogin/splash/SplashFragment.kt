package com.app.dubaiculture.ui.preLogin.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentSplashBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.preLogin.splash.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_forgot_.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSplashBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch {
            navigate()
        }
    }

    private suspend fun navigate() {
        delay(3000)
        application.auth.isLoggedIn = true
        val user = splashViewModel.getUserIfExists()
        if (user != null) {
            application.auth.user = user
//            activity.killSessionAndStartNewActivity(PostLoginActivity::class.java)
        }
        findNavController(this).navigate(R.id.action_splashFragment_to_loginFragment)

        }


}