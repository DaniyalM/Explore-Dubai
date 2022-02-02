package com.dubaiculture.ui.postLogin.popular_service.myservices.adapters

import android.content.Context
import com.dubaiculture.R
import com.dubaiculture.data.repository.eservices.local.EServiceStatus
import com.dubaiculture.databinding.ItemEserviceStatusBinding
import com.xwray.groupie.databinding.BindableItem

data class ServiceStatusListItem(
    val resLayout: Int = R.layout.item_eservice_status,
    val context: Context,
    val serviceStatus: EServiceStatus
) : BindableItem<ItemEserviceStatusBinding>() {

    override fun getLayout() = resLayout
    override fun bind(viewBinding: ItemEserviceStatusBinding, position: Int) {
        viewBinding.serviceStatus = serviceStatus
    }

}