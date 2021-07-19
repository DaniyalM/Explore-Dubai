package com.app.dubaiculture.ui.postLogin.popular_service.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.TabsHeaderClick
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.ServiceListingHeaderItem
import com.app.dubaiculture.ui.postLogin.popular_service.models.ServiceHeader
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ServicesListingHeaderItemSelector(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs),
    TabsHeaderClick {
    val groupAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var list: List<ServiceHeader>? = null
    var recyclerView: RecyclerView? = null
    private var _headerPosition: MutableLiveData<Int> =
        MutableLiveData(ServicesHeaderItemSelector.SERVICE_CLICK_CHECKER_FLAG)
    var headerPosition: LiveData<Int> = _headerPosition

    companion object {
        var PREVIOUS_POSITION: Int = 0
        var SERVICE_CLICK_CHECKER_FLAG: Int = 0
    }

    init {

        val view = inflate(context, R.layout.single_selection_item_cell, null)
        recyclerView = view.findViewById(R.id.rVgeneric)
        recyclerView?.let {
            it.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addView(view)
            it.adapter = groupAdapter
        }

    }

    @JvmName("ServiceHeaders")
    fun initialize(
        list: List<ServiceHeader>,
    ) {
        this.list = list
        itemAddition()
    }


    fun itemAddition() {
        if (groupAdapter.itemCount > 0) {
            groupAdapter.clear()
        }

        list?.forEachIndexed { index, model ->
            if (SERVICE_CLICK_CHECKER_FLAG == index) {
                isSelected = true
                positionUpdate(SERVICE_CLICK_CHECKER_FLAG)
            }
            groupAdapter.add(
                ServiceListingHeaderItem(
                    displayValue = model.title,
                    data = model,
                    isSelected = isSelected,
                    selectedInnerImg = model.selectedIconString,
                    unSelectedInnerImg = model.unSelectedIconString,
                    progressListener = this,
                    colorBg = model.color
                )
            )
        }
    }

    fun itemIndexUpdate() {
        list?.get(PREVIOUS_POSITION)?.let {
            groupAdapter.notifyItemChanged(
                PREVIOUS_POSITION, ServiceListingHeaderItem(
                    displayValue = it.title,
                    data = it,
                    isSelected = isSelected,
                    selectedInnerImg = it.selectedIconString,
                    unSelectedInnerImg = it.unSelectedIconString,
                    progressListener = this,
                    colorBg = it.color
                )
            )
        }
    }

    override fun onClick(position: Int) {
        PREVIOUS_POSITION = SERVICE_CLICK_CHECKER_FLAG
        positionUpdate(position)
        itemIndexUpdate()
    }

    fun positionUpdate(position: Int) {
        SERVICE_CLICK_CHECKER_FLAG = position
        recyclerView?.smoothScrollToPosition(position)
        _headerPosition.value = position
//        bus?.post(PopularServiceBus.HeaderItemClick(position))
    }


}