package com.app.dubaiculture.ui.postLogin.more.privacytermscondition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentPrivacyTermConditionBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyTermConditionFragment : BaseBottomSheetFragment<FragmentPrivacyTermConditionBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentPrivacyTermConditionBinding.inflate(inflater,container,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getString(Constants.NavBundles.TERMS_CONDITION_PRIVACY_POLICY)
        }

    }
}