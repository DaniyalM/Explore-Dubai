package com.app.dubaiculture.ui.preLogin.splash

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentSplashBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.preLogin.splash.viewmodels.SplashViewModel
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker
import com.estimote.coresdk.common.requirements.SystemRequirementsHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class   SplashFragment : BaseFragment<FragmentSplashBinding>() {
//    private val splashViewModel: SplashViewModel by viewModels()

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?,
    ) = FragmentSplashBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            navigate()
        }

    }


    private suspend fun navigate() {

        delay(3000)
        application.auth.isLoggedIn = true
//        findNavController(this).navigate(R.id.action_splashFragment_to_onBoardingFragment)
        findNavController(this).navigate(R.id.action_splashFragment_to_loginFragment)

    }





}