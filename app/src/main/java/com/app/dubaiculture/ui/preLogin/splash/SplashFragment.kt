package com.app.dubaiculture.ui.preLogin.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentSplashBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.dataStore.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    //    private val splashViewModel: SplashViewModel by viewModels()
    @Inject
    lateinit var dataStoreManager: DataStoreManager
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
        navigate(R.id.action_splashFragment_to_onboarding_navigation)
//        if(dataStoreManager.getBoolean(preferencesKey(Constants.DataStore.SKIP)) == true){
//            navigate(R.id.action_splashFragment_to_loginFragment)
//
//        }else{
    //    navigate(R.id.action_splashFragment_to_onboarding_navigation)

//        }
    }


}