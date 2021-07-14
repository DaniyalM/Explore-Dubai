package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.PopularServices
import com.app.dubaiculture.data.repository.explore.local.models.ServiceBookings
import com.app.dubaiculture.databinding.ItemLayoutTabHeaderBinding
import com.app.dubaiculture.databinding.ItemsBookATicketLayoutBinding
import com.app.dubaiculture.databinding.PopularServiceInnerItemCellBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.popular_service.detail.ServiceDetailFragment.Companion.headerSelectionFlag
import com.app.dubaiculture.ui.postLogin.popular_service.detail.TabHeaders
import com.google.android.material.card.MaterialCardView
import com.xwray.groupie.databinding.BindableItem

class PopularServiceListItem<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val services: PopularServices? = null,
    val servicesBookings: ServiceBookings? = null,
    val resLayout: Int = R.layout.popular_service_inner_item_cell,
    val tabHeader: TabHeaders? = null,
    val context: Context?=null,
    val isSelected: Boolean=false,
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
//            is ItemLayoutTabHeaderBinding -> {
//                viewBinding.apply {
//                    viewBinding.headerTitle.apply {
//                        text = tabHeader?.name
//                        setOnClickListener {
//                          rowClickListener?.rowClickListener(position)
//                        }
//                    }
//
//                    if (position == headerSelectionFlag) {
//                        viewBinding.highlighter.visibility = View.VISIBLE
//
//                    } else {
//                        viewBinding.highlighter.visibility = View.GONE
//                    }
//                }
//            }

        }
    }


}

