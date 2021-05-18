package com.app.dubaiculture.ui.postLogin.more.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentProfileBinding
import com.app.dubaiculture.ui.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentProfileBinding.inflate(inflater, container, false)
}