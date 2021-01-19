package com.app.dubaiculture.ui.postLogin.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentExploreBinding
import com.app.dubaiculture.ui.base.BaseFragment

class ExploreFragment : BaseFragment<FragmentExploreBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentExploreBinding.inflate(inflater, container, false)


    fun getRecyclerView() = binding.rvExplore

}