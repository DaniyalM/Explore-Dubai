package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.PopularServices
import com.app.dubaiculture.data.repository.explore.local.models.ServiceBookings
import com.app.dubaiculture.data.repository.explore.local.models.ServiceStatus
import com.app.dubaiculture.databinding.ItemServiceCompletedLayoutBinding
import com.app.dubaiculture.databinding.ItemsBookATicketLayoutBinding
import com.app.dubaiculture.databinding.PopularServiceInnerItemCellBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem

class PopularServiceListItem<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val services: PopularServices? = null,
    val servicesBookings: ServiceBookings? = null,
    val myServiceStatus : ServiceStatus?=null,
    val resLayout: Int = R.layout.popular_service_inner_item_cell,
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is PopularServiceInnerItemCellBinding -> {
                viewBinding.popularServices = services
            }
            is ItemsBookATicketLayoutBinding -> {
                viewBinding.apply {
                    popularServices=servicesBookings
                }
            }
            is ItemServiceCompletedLayoutBinding ->{
                viewBinding.apply {
                    var isExpand = false
                    serviceStatus = myServiceStatus
                    if(myServiceStatus?.completed ==false){
                        llCompleted.visibility = View.GONE
                        llPending.visibility = View.VISIBLE
                    }
                    llViewStatus.setOnClickListener {
                        if(!isExpand){
                            llExpand.visibility = View.VISIBLE
                        }else{
                            llExpand.visibility = View.GONE
                        }
                    }
                }


            }

        }
    }
}

