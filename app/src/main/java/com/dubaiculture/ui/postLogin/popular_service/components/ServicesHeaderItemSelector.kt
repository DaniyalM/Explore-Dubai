package com.dubaiculture.ui.postLogin.popular_service.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.ui.postLogin.attractions.clicklisteners.TabsHeaderClick
import com.dubaiculture.ui.postLogin.popular_service.adapter.ServicesHeaderItems
import com.dubaiculture.ui.postLogin.popular_service.models.ServiceHeader

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ServicesHeaderItemSelector(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs), TabsHeaderClick {
    val groupAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var list: List<ServiceHeader>? = null
    var recyclerView: RecyclerView? = null
    private var _headerPosition: MutableLiveData<Int> = MutableLiveData(SERVICE_CLICK_CHECKER_FLAG)
    var headerPosition: LiveData<Int> = _headerPosition

    companion object {
        var previousPosition: Int = 0
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

    private fun getDrawableFromId(resId: Int?): Drawable? {
        resId?.let {
            return if (it == 0) null
            else ResourcesCompat.getDrawable(resources, it, null)
        }
        return null
    }


    fun itemIndexUpdate() {
        list?.get(previousPosition)?.let {
            groupAdapter.notifyItemChanged(
                previousPosition, ServicesHeaderItems(
                    displayValue = it.title ?: "test",
                    data = list,
                    isSelected = isSelected,
                    selectedInnerImg = getDrawableFromId(it.selectedIcon),
                    unSelectedInnerImg = getDrawableFromId(it.unselectedIcon),
                    progressListener = this,
                    colorBg = it.color
                )
            )
        }
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
                ServicesHeaderItems(
                    displayValue = model.title,
                    data = list,
                    isSelected = isSelected,
                    selectedInnerImg = getDrawableFromId(model.selectedIcon),
                    unSelectedInnerImg = getDrawableFromId(model.unselectedIcon),
                    progressListener = this,
                    colorBg = model.color
                )
            )
        }
    }

    override fun onClick(position: Int) {
        previousPosition = SERVICE_CLICK_CHECKER_FLAG
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