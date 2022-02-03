package com.dubaiculture.ui.postLogin.popular_service.myservices.adapters

import android.content.Context
import com.dubaiculture.R
import com.dubaiculture.data.repository.eservices.local.EServiceStatus
import com.dubaiculture.databinding.ItemEserviceStatusBinding
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.toString
import com.xwray.groupie.databinding.BindableItem
import java.text.SimpleDateFormat

data class ServiceStatusListItem(
    val resLayout: Int = R.layout.item_eservice_status,
    val context: Context,
    val serviceStatus: EServiceStatus
) : BindableItem<ItemEserviceStatusBinding>() {

    override fun getLayout() = resLayout
    override fun bind(viewBinding: ItemEserviceStatusBinding, position: Int) {
        viewBinding.serviceStatus = serviceStatus
        viewBinding.tvDate.text =
            SimpleDateFormat(Constants.DateFormats.TIME_STAMP_WITH_ZONE).parse(serviceStatus.request.dateTime)
                .toString(Constants.DateFormats.DD_MMM_YYYY)

    }

}