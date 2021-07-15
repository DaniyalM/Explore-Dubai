package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.PopularServices
import com.app.dubaiculture.data.repository.explore.local.models.ServiceBookings
import com.app.dubaiculture.databinding.ItemsBookATicketLayoutBinding
import com.app.dubaiculture.databinding.PopularServiceInnerItemCellBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.popular_service.detail.TabHeaders
import com.xwray.groupie.databinding.BindableItem

class PopularServiceListItem<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val services: PopularServices? = null,
    val servicesBookings: ServiceBookings? = null,
    val resLayout: Int = R.layout.popular_service_inner_item_cell,
    val tabHeader: TabHeaders? = null,
    val context: Context? = null,
    val isSelected: Boolean = false,
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is PopularServiceInnerItemCellBinding -> {
                viewBinding.popularServices = services
            }
            is ItemsBookATicketLayoutBinding -> {
                viewBinding.apply {
                    popularServices = servicesBookings
                }
            }

        }
    }


}

