package com.app.dubalculture.ui.preLogin.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.app.dubalculture.R
import com.app.dubalculture.databinding.FragmentSplashBinding
import com.app.dubalculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSplashBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lifecycleScope.launch {
            navigate()
        }
    }

    private suspend fun navigate() {
        delay(1000)
        findNavController(this).navigate(R.id.action_splashFragment_to_loginFragment)
    }

}