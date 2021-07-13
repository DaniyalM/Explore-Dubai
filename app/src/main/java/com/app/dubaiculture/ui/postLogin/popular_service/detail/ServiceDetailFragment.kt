package com.app.dubaiculture.ui.postLogin.popular_service.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentServiceDetailFragmentBinding
import com.app.dubaiculture.ui.base.BaseFragment

//@AndroidEntryPoint
class ServiceDetailFragment : BaseFragment<FragmentServiceDetailFragmentBinding>() {

    private var headerList:MutableList<String> = mutableListOf(
        ""
    )


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentServiceDetailFragmentBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

    }

    private fun tabInit(){
        binding.serviceDetailHeaderTabs.apply {
            addTab(this.newTab().setText(""))
        }
    }
}