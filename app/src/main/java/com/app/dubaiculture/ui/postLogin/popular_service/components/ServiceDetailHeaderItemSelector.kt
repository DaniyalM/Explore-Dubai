package com.app.dubaiculture.ui.postLogin.popular_service.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.Description
import com.app.dubaiculture.data.repository.popular_service.local.models.EServicesDetail
import com.app.dubaiculture.data.repository.popular_service.local.models.Payment
import com.app.dubaiculture.data.repository.popular_service.local.models.Procedure
import com.app.dubaiculture.databinding.ItemsServiceDetailDescLayoutBinding
import com.app.dubaiculture.databinding.ItemsServiceDetailPaymentLayoutBinding
import com.app.dubaiculture.databinding.ItemsServiceDetailProcedureLayoutBinding
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.TabsHeaderClick
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailHeaderItems
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailListingItems
import com.app.dubaiculture.ui.postLogin.popular_service.detail.TabHeaders
import com.app.dubaiculture.utils.AppConfigUtils.SERVICE_DETAIL_HEADER_FLAG
import com.app.dubaiculture.utils.getSnapPosition
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.util.*


class ServiceDetailHeaderItemSelector(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs), TabsHeaderClick {
    var groupAdapterRow: GroupAdapter<GroupieViewHolder>? = null
    var groupAdapterCol: GroupAdapter<GroupieViewHolder>? = null
    private var _headerPosition: MutableLiveData<Int> = MutableLiveData(SERVICE_DETAIL_HEADER_FLAG)
    var headerPosition: LiveData<Int> = _headerPosition
    var recyclerViewRow: RecyclerView? = null
    var recyclerViewCol: RecyclerView? = null
    var blockColumnScroll = false
    private var firstVisibleInListview = 0

    var looper = TabHeaders.values()


    companion object {
        var previousPosition: Int = 0
    }

    init {
        val view = inflate(context, R.layout.single_selection_item_cell, null)
        recyclerViewRow = view.findViewById(R.id.rVgeneric)
        recyclerViewCol = view.findViewById(R.id.rVgenericColumn)
        recyclerViewCol?.visibility = View.VISIBLE


        groupAdapterRow = GroupAdapter()
        recyclerViewRow?.let {
            it.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            it.adapter = groupAdapterRow
        }

        groupAdapterCol = GroupAdapter()
        recyclerViewCol?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = groupAdapterCol
            val snapHelper = LinearSnapHelper()
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
        groupAdapterCol?.add(
            ServiceDetailListingItems<ItemsServiceDetailDescLayoutBinding, Description>(
                eService = eServicesDetail.description?.get(0)
            )
        )
        groupAdapterCol?.add(
            ServiceDetailListingItems<ItemsServiceDetailProcedureLayoutBinding, Procedure>(
                eService = eServicesDetail.procedure?.get(0),
                resLayout = R.layout.items_service_detail_procedure_layout
            )
        )
        groupAdapterCol?.add(
            ServiceDetailListingItems<ItemsServiceDetailPaymentLayoutBinding, Payment>(
                eService = eServicesDetail.payments?.get(0),
                resLayout = R.layout.items_service_detail_payment_layout
            )
        )

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
        looper.get(previousPosition).let {
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
        _headerPosition.value = position
        recyclerViewRow?.smoothScrollToPosition(position)


    }

    override fun onClick(position: Int) {
        previousPosition = SERVICE_DETAIL_HEADER_FLAG
        positionUpdate(position)
        itemIndexUpdate()
        if (!blockColumnScroll) {
            recyclerViewCol?.smoothScrollToPosition(position)
        }


    }

    fun destroySelector() {
        recyclerViewRow = null
        recyclerViewCol = null
        groupAdapterRow?.clear()
        groupAdapterCol?.clear()
    }

}