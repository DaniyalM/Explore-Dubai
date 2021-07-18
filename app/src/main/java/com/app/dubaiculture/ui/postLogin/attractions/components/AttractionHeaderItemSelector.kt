package com.app.dubaiculture.ui.postLogin.attractions.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionHeaderItems
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionHeaderClick
import com.app.dubaiculture.ui.postLogin.attractions.services.AttractionServices
import com.app.dubaiculture.utils.AppConfigUtils
import com.app.dubaiculture.utils.AppConfigUtils.clickCheckerFlag
import com.squareup.otto.Bus
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class AttractionHeaderItemSelector(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs), AttractionHeaderClick {
    val groupAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var list: List<AttractionCategory>? = null
    var recyclerView: RecyclerView? = null

    //    var bus: Bus? = null
    private var _headerPosition: MutableLiveData<Int> =
        MutableLiveData(clickCheckerFlag)
    var headerPosition: LiveData<Int> = _headerPosition

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

    }

    @JvmName("attractionHeaders")
    fun initialize(
        list: List<AttractionCategory>,
//        bus: Bus
    ) {
        this.list = list
//        this.bus = bus
        itemsAddnUpdation()
    }

    fun itemsAddnUpdation(isUpdate: Boolean = false) {
        var isSelected = false

        if (!isUpdate) {
            if (groupAdapter.itemCount > 0) {
                groupAdapter.clear()
            }
            list?.forEachIndexed { index, model ->
                if (clickCheckerFlag == index) {
                    isSelected = true
                    positionUpdate(clickCheckerFlag)
                }
                groupAdapter.add(
                    AttractionHeaderItems(
                        displayValue = model.title!!,
                        data = list,
                        isSelected = isSelected,
                        selectedInnerImg = model.selectedSvg,
                        unSelectedInnerImg = model.icon,
                        progressListener = this,
                        colorBg = model.color
                    )
                )
            }


        } else {
            list?.get(previousPosition)?.let {
                groupAdapter.notifyItemChanged(
                    previousPosition, AttractionHeaderItems(
                        displayValue = it.title!!,
                        data = list,
                        isSelected = isSelected,
                        selectedInnerImg = it.selectedSvg,
                        colorBg = it.color,
                        unSelectedInnerImg = it.icon,
                        progressListener = this
                    )
                )
            }
        }

    }

    private fun getDrawableFromId(resId: Int?): Drawable? {
        resId?.let {
            return if (it == 0) null
            else ResourcesCompat.getDrawable(resources, it, null)
        }
        return null
    }

    override fun onClick(position: Int) {
        previousPosition = clickCheckerFlag
        positionUpdate(position)
        itemsAddnUpdation(true)
    }

    fun positionUpdate(position: Int) {
        clickCheckerFlag = position
        recyclerView?.smoothScrollToPosition(position)
        _headerPosition.value=position
//        bus?.post(AttractionServices.CategoryClick(position))
    }


}