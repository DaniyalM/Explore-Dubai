package com.app.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.data.repository.popular_service.local.models.Description
import com.app.dubaiculture.databinding.ItemsServiceDetailDescLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment

class DescriptionPageFragment(description :List<Description>): BaseFragment<ItemsServiceDetailDescLayoutBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= ItemsServiceDetailDescLayoutBinding.inflate(inflater,container,false)


}