package com.app.dubaiculture.ui.postLogin.popular_service.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionHeaderClick
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.ServiceDetailHeaderItems
import com.app.dubaiculture.ui.postLogin.popular_service.detail.TabHeaders
import com.app.dubaiculture.utils.AppConfigUtils
import com.app.dubaiculture.utils.AppConfigUtils.SERVICE_DETAIL_HEADER_FLAG
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ServiceDetailHeaderItemSelector(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs), AttractionHeaderClick {
    val groupAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var _headerPosition: MutableLiveData<Int> = MutableLiveData(SERVICE_DETAIL_HEADER_FLAG)
    var headerPosition: LiveData<Int> = _headerPosition
    var recyclerView: RecyclerView? = null
    companion object {
        var previousPosition: Int = 0
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
        itemAddition()

    }

    fun itemAddition() {
        if (groupAdapter.itemCount > 0) {
            groupAdapter.clear()
        }

        TabHeaders.values().forEachIndexed { index, model ->
            if (SERVICE_DETAIL_HEADER_FLAG == index) {
                isSelected = true
                positionUpdate(SERVICE_DETAIL_HEADER_FLAG)
            }
            groupAdapter.add(
                ServiceDetailHeaderItems(
                    displayValue = model.name,
                    isSelected = isSelected,
                    progressListener = this
                )
            )
        }
    }

    fun positionUpdate(position: Int) {
        SERVICE_DETAIL_HEADER_FLAG = position
        recyclerView?.smoothScrollToPosition(position)
        _headerPosition.value = position
    }

    override fun onClick(position: Int) {
        previousPosition = SERVICE_DETAIL_HEADER_FLAG
        positionUpdate(position)
        itemIndexUpdate()
    }

    fun itemIndexUpdate() {
        TabHeaders.values().get(previousPosition).let {
            groupAdapter.notifyItemChanged(
                previousPosition,
                ServiceDetailHeaderItems(
                    displayValue = it.name,
                    isSelected = isSelected,
                    progressListener = this
                )
            )
        }
    }


}