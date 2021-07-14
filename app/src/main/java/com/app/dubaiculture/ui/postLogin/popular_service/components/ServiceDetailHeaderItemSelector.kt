package com.app.dubaiculture.ui.postLogin.popular_service.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionHeaderClick
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailHeaderItems
import com.app.dubaiculture.ui.postLogin.popular_service.detail.TabHeaders
import com.app.dubaiculture.utils.AppConfigUtils.SERVICE_DETAIL_HEADER_FLAG
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ServiceDetailHeaderItemSelector(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs), AttractionHeaderClick {
    var groupAdapterRow: GroupAdapter<GroupieViewHolder>? = null
    var groupAdapterCol: GroupAdapter<GroupieViewHolder>? = null
    private var _headerPosition: MutableLiveData<Int> = MutableLiveData(SERVICE_DETAIL_HEADER_FLAG)
    var headerPosition: LiveData<Int> = _headerPosition
    var recyclerViewRow: RecyclerView? = null
    var recyclerViewCol: RecyclerView? = null
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
            addView(view)
            it.adapter = groupAdapterRow
        }

        groupAdapterCol = GroupAdapter()
        recyclerViewCol?.let {
            it.layoutManager =
                LinearLayoutManager(context)
            addView(view)
            it.adapter = groupAdapterCol
        }

        itemAddition()

    }

    fun initializeSelector() {}
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
                    displayValue = model.name,
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
        recyclerViewRow?.smoothScrollToPosition(position)
        _headerPosition.value = position
    }

    override fun onClick(position: Int) {
        previousPosition = SERVICE_DETAIL_HEADER_FLAG
        positionUpdate(position)
        itemIndexUpdate()
    }

    fun destroySelector() {
        recyclerViewRow = null
        recyclerViewCol = null
        groupAdapterRow?.clear()
        groupAdapterCol?.clear()
    }

}