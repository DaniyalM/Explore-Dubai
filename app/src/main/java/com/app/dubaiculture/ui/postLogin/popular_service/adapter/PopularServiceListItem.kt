package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.ServiceBookings
import com.app.dubaiculture.data.repository.explore.local.models.ServiceStatus
import com.app.dubaiculture.data.repository.popular_service.local.models.EService
import com.app.dubaiculture.data.repository.popular_service.local.models.EServices
import com.app.dubaiculture.data.repository.popular_service.local.models.PopularServices
import com.app.dubaiculture.databinding.ItemServiceCompletedPendingLayoutBinding
import com.app.dubaiculture.databinding.ItemsBookATicketLayoutBinding
import com.app.dubaiculture.databinding.ItemsServiceListingLayoutBinding
import com.app.dubaiculture.databinding.PopularServiceInnerItemCellBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem

class PopularServiceListItem<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val services: PopularServices? = null,
    val servicesBookings: ServiceBookings? = null,
    val myServiceStatus : ServiceStatus?=null,
    val eService : EService?=null,
    val context: Context?=null,
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
            is ItemServiceCompletedPendingLayoutBinding ->{
                viewBinding.apply {
                    var isExpand = false
                    serviceStatus = myServiceStatus
                    tvViewStatus.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.plus,0)
                    if(myServiceStatus?.completed ==false){
                        llCompleted.visibility = View.GONE
                        llPending.visibility = View.VISIBLE
                    }
                    llViewStatus.setOnClickListener {
                        if(!isExpand){
                            isExpand = true
                            llExpand.visibility = View.VISIBLE
                            tvViewStatus.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.remove,0)
                        }else{
                            isExpand =false
                            llExpand.visibility = View.GONE
                            tvViewStatus.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.plus,0)
                        }
                    }
                }
            }
            is ItemsServiceListingLayoutBinding ->{

                viewBinding.apply {
                    setAnimation(rootView,position,context!!)
                    serviceListing = eService
                }
            }
        }
    }
    private fun setAnimation(viewToAnimate: View, position: Int,context: Context) {
        // If the bound view wasn't previously displayed on screen, it's animated
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        viewToAnimate.startAnimation(animation)
    }

}

