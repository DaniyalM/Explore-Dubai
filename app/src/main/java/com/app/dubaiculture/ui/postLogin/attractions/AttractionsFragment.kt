package com.app.dubaiculture.ui.postLogin.attractions

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentAttractionsBinding
import com.app.dubaiculture.ui.base.BaseFragment

class AttractionsFragment : BaseFragment<FragmentAttractionsBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAttractionsBinding.inflate(inflater, container, false)
}