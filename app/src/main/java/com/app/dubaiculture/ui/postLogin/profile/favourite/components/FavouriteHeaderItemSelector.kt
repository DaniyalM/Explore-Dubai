package com.app.dubaiculture.ui.postLogin.profile.favourite.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.TabsHeaderClick
import com.app.dubaiculture.ui.postLogin.profile.favourite.services.FavouriteServices
import com.app.dubaiculture.ui.postLogin.profile.favourite.adapters.FavouriteHeaderItems
import com.app.dubaiculture.ui.postLogin.profile.favourite.models.FavouriteHeader
import com.app.dubaiculture.utils.AppConfigUtils.favouriteClickCheckerFlag
import com.squareup.otto.Bus
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class FavouriteHeaderItemSelector(context: Context, attrs: AttributeSet) :
        FrameLayout(context, attrs), TabsHeaderClick {
    val groupAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var list: List<FavouriteHeader>? = null
    var recyclerView: RecyclerView? = null
    var bus: Bus? = null

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


    @JvmName("favouriteHeaders")
    fun initialize(
            list: List<FavouriteHeader>,
            bus: Bus
    ) {
        this.list = list
        this.bus = bus
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
            groupAdapter.notifyItemChanged(previousPosition, FavouriteHeaderItems(
                    displayValue = it.title!!,
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
            if (favouriteClickCheckerFlag == index) {
                isSelected = true
                positionUpdate(favouriteClickCheckerFlag)
            }
            groupAdapter.add(
                    FavouriteHeaderItems(
                            displayValue = model.title!!,
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
        previousPosition = favouriteClickCheckerFlag
        positionUpdate(position)
        itemIndexUpdate()
    }

    fun positionUpdate(position: Int) {
        favouriteClickCheckerFlag = position
        recyclerView?.smoothScrollToPosition(position)
        bus?.post(FavouriteServices.HeaderItemClick(position))
    }
}