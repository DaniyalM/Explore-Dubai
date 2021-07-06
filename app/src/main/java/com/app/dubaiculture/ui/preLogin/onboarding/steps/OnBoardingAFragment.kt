package com.app.dubaiculture.ui.preLogin.onboarding.steps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentOnBoardingABinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.dataStore.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingAFragment : BaseFragment<FragmentOnBoardingABinding>(), View.OnClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentOnBoardingABinding.inflate(inflater,container,false)

    override fun onClick(v: View?) {
       when(v?.id){
//           R.id.btn_skip->{
//
//           }
       }
    }
}