package com.dubaiculture.ui.preLogin.onboarding.steps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentOnBoardingABinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.dataStore.DataStoreManager
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