package com.dubaiculture.ui.postLogin.more.termsandconditions

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dubaiculture.databinding.FragmentTermConditionBinding
import com.dubaiculture.ui.base.BaseFragment

class TermsAndConditionsFragment : BaseFragment<FragmentTermConditionBinding>() {
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentTermConditionBinding.inflate(inflater, container, false)
}