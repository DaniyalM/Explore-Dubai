package com.dubaiculture.ui.preLogin.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentSplashBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.dataStore.DataStoreManager
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
//        navigate(R.id.action_splashFragment_to_onBoardingFragment2)
        if(dataStoreManager.getBoolean(preferencesKey(Constants.DataStore.SKIP)) == true){
//            navigate(R.id.action_splashFragment_to_loginFragment)
            navigateByDirections(SplashFragmentDirections.actionSplashFragmentToLoginFragment())

        }else{
            navigateByDirections(SplashFragmentDirections.actionSplashFragmentToOnBoardingNavigation())



        }
    }


}