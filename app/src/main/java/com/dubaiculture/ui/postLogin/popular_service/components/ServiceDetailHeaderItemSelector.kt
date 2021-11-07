package com.dubaiculture.ui.postLogin.popular_service.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.*
import com.dubaiculture.databinding.*
import com.dubaiculture.ui.postLogin.attractions.clicklisteners.TabsHeaderClick
import com.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailHeaderItems
import com.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailListingItems
import com.dubaiculture.ui.postLogin.popular_service.detail.TabHeaders
import com.dubaiculture.utils.AppConfigUtils.SERVICE_DETAIL_HEADER_FLAG
import com.dubaiculture.utils.getSnapPosition
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.util.*


class ServiceDetailHeaderItemSelector(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs), TabsHeaderClick {
    var groupAdapterRow: GroupAdapter<GroupieViewHolder>? = null
    var groupAdapterCol: GroupAdapter<GroupieViewHolder>? = null
    var recyclerViewRow: RecyclerView? = null
    var recyclerViewCol: RecyclerView? = null
    var recyclerViewColnested: NestedScrollView? = null
    var blockColumnScroll = false


    var looper = TabHeaders.values()


    companion object {
        var previousPosition: Int = 0
    }

    init {
        val view = inflate(context, R.layout.single_selection_item_cell, null)
        recyclerViewRow = view.findViewById(R.id.rVgeneric)
        recyclerViewCol = view.findViewById(R.id.rVgenericColumn)
        recyclerViewColnested = view.findViewById(R.id.rVgenericColumnnested)
        recyclerViewColnested?.visibility = View.VISIBLE


        groupAdapterRow = GroupAdapter()
        recyclerViewRow?.let {
            it.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            it.adapter = groupAdapterRow
        }


        //  scrolling on vertical in view
        groupAdapterCol = GroupAdapter()
        recyclerViewCol?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = groupAdapterCol
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {
                            blockColumnScroll = true
                            onClick(snapHelper.getSnapPosition(recyclerView))
                            blockColumnScroll = false
                        }

                    }

                }


            })

        }
        addView(view)
        itemAddition()

    }

    fun initializeSelector(eServicesDetail: EServicesDetail) {
        columnAddition(eServicesDetail)
    }

    fun columnAddition(eServicesDetail: EServicesDetail) {
        if (groupAdapterCol?.itemCount!! > 0) {
            groupAdapterCol?.clear()
        }
        val description = eServicesDetail.description?.get(0)
        val procedure = eServicesDetail.procedure?.get(0)
        val requiredDocument = eServicesDetail.requiredDocument?.get(0)
        val payments = eServicesDetail.payments?.get(0)

        val descriptionItem =
            ServiceDetailListingItems<ItemsServiceDetailDescLayoutBinding, Description>(eService = description)
        val procedureItem =
            ServiceDetailListingItems<ItemsServiceDetailInnerListingLayoutBinding, Procedure>(
                eService = procedure,
                resLayout = R.layout.items_service_detail_inner_listing_layout
            )
        val requiredDocumentItem =
            ServiceDetailListingItems<ItemsServiceDetailInnerListingLayoutBinding, RequiredDocument>(
                eService = requiredDocument,
                resLayout = R.layout.items_service_detail_inner_listing_layout
            )
        val paymentsItem =
            ServiceDetailListingItems<ItemsServiceDetailInnerListingLayoutBinding, Payment>(
                eService = payments,
                resLayout = R.layout.items_service_detail_inner_listing_layout
            )
        groupAdapterCol?.add(descriptionItem)
        groupAdapterCol?.add(procedureItem)
        groupAdapterCol?.add(requiredDocumentItem)
        groupAdapterCol?.add(paymentsItem)

    }

    fun itemAddition() {
        if (groupAdapterRow?.itemCount!! > 0) {
            groupAdapterRow?.clear()
        }

        looper.forEachIndexed { index, model ->
            if (SERVICE_DETAIL_HEADER_FLAG == index) {
                isSelected = true
                positionUpdate(SERVICE_DETAIL_HEADER_FLAG)
            }
            groupAdapterRow?.add(
                ServiceDetailHeaderItems(
                    displayValue = model.name.substring(0, 1)
                        .toUpperCase(Locale.ROOT) + model.name.substring(1)
                        .toLowerCase(Locale.ROOT),
                    isSelected = isSelected,
                    progressListener = this
                )
            )
        }
    }

    fun itemIndexUpdate() {
        looper[previousPosition].let {
            groupAdapterRow?.notifyItemChanged(
                previousPosition,
                ServiceDetailHeaderItems(
                    displayValue = it.name,
                    isSelected = isSelected,
                    progressListener = this
                )
            )
        }
    }

    fun positionUpdate(position: Int) {
        SERVICE_DETAIL_HEADER_FLAG = position
        recyclerViewRow?.smoothScrollToPosition(position)


    }

    override fun onClick(position: Int) {
        previousPosition = SERVICE_DETAIL_HEADER_FLAG
        positionUpdate(position)
        itemIndexUpdate()



        if (!blockColumnScroll) {

            recyclerViewColnested?.post {
                when (position) {
                    1 -> checkPosition(position)
                    2 -> checkPosition(position)
                    3 -> checkPosition(position)
                    else -> recyclerViewColnested?.fullScroll(View.FOCUS_UP)
                }
            }
        }


    }

    private fun checkPosition(position: Int) {
        if (SERVICE_DETAIL_HEADER_FLAG > position) {
            recyclerViewColnested?.fullScroll(View.FOCUS_UP)
            recyclerViewCol?.smoothScrollToPosition(position)
        } else {
            recyclerViewCol?.smoothScrollToPosition(position)
            recyclerViewColnested?.fullScroll(View.FOCUS_DOWN)
        }
    }

    fun destroySelector() {
        recyclerViewRow = null
        recyclerViewCol = null
        groupAdapterRow?.clear()
        groupAdapterCol?.clear()
    }

}