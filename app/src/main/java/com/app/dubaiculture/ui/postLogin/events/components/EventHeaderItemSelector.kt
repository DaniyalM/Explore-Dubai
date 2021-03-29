package com.app.dubaiculture.ui.postLogin.events.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionHeaderClick
import com.app.dubaiculture.ui.postLogin.events.EventFilterFragment
import com.app.dubaiculture.ui.postLogin.events.HeaderModel
import com.app.dubaiculture.ui.postLogin.events.adapters.EventHeaderItems
import com.app.dubaiculture.ui.postLogin.events.adapters.EventPagerAdapter
import com.app.dubaiculture.utils.AppConfigUtils.clickCheckerFlag
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class EventHeaderItemSelector(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs), AttractionHeaderClick {
    private var selectedTextColor: Int? = null
    private var unSelectedTextColor: Int? = null
    private var selectedBackground: Int? = null
    private var unSelectedBackground: Int? = null
    private var selectedInnerImg: Int? = null
    private var unSelectedInnerImg: Int? = null
    private val groupAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var list: List<HeaderModel>? = null
    private var eventPager: ViewPager2? = null
    private var recyclerView: RecyclerView? = null

    companion object {
        var previousPosition: Int = 0
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs,
            R.styleable.HorizontalAttractionSelector)
        for (i in 0..typeArray.indexCount) {
            when (val attr = typeArray.getIndex(i)) {
                R.styleable.HorizontalAttractionSelector_selectedTextColor1 -> {
                    selectedTextColor = typeArray.getColor(attr, Color.RED)
                }
                R.styleable.HorizontalAttractionSelector_unSelectedTextColor1 -> {
                    unSelectedTextColor = typeArray.getColor(attr, Color.GRAY)
                }
                R.styleable.HorizontalAttractionSelector_selectedBackground1 -> {
                    selectedBackground = typeArray.getResourceId(attr, 0)
                }
                R.styleable.HorizontalAttractionSelector_unSelectedBackground1 -> {
                    unSelectedBackground = typeArray.getResourceId(attr, 0)
                }

//                inner img

                R.styleable.HorizontalAttractionSelector_selectedInnerImg -> {
                    selectedInnerImg = typeArray.getResourceId(attr, 0)
                }
                R.styleable.HorizontalAttractionSelector_unSelectedInnerImg -> {
                    unSelectedInnerImg = typeArray.getResourceId(attr, 0)
                }
            }
        }
        typeArray.recycle()
        val view = inflate(context, R.layout.attractions_item_selector, null)
        recyclerView = view.findViewById(R.id.rVgeneric)

        recyclerView?.let {
            it.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addView(view)
            it.adapter = groupAdapter

        }
    }

    @JvmName("eventHeader")
    fun initialize(
        list: List<HeaderModel>,
        attractionPager: ViewPager2? = null,
        eventFilterFragment: EventFilterFragment,
    ) {
        this.list = list
        this.eventPager = attractionPager
        if (groupAdapter.itemCount == 0) {
            this.eventPager?.adapter = EventPagerAdapter(eventFilterFragment).apply {
                provideListToPager(list)
            }
            itemsAddnUpdation()
        }
    }

    fun itemsAddnUpdation(isUpdate: Boolean = false) {

        var isSelected = false
        if (!isUpdate) {
            list?.forEachIndexed { index, model ->
                if (clickCheckerFlag == index) {
                    isSelected = true
                    positionUpdate(clickCheckerFlag)
                }
                groupAdapter.add(
                    EventHeaderItems(
                        displayValue = model.title,
                        data = list,
                        isSelected = isSelected,
                        selectedTextColor = selectedTextColor,
                        unSelectedTextColor = unSelectedTextColor,
                        selectedBackground = getDrawableFromId(selectedBackground),
                        unSelectedBackground = getDrawableFromId(unSelectedBackground),
                        progressListener = this
                    )
                )
            }
        } else {
            list?.get(previousPosition)?.let {

                groupAdapter.notifyItemChanged(previousPosition, EventHeaderItems(
                    displayValue = it.title,
                    data = list,
                    isSelected = isSelected,
                    selectedTextColor = selectedTextColor,
                    unSelectedTextColor = unSelectedTextColor,
                    selectedBackground = getDrawableFromId(selectedBackground),
                    unSelectedBackground = getDrawableFromId(unSelectedBackground),
                    progressListener = this)
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
        positionUpdate(position)
        itemsAddnUpdation(true)
    }

    fun positionUpdate(position: Int) {
        previousPosition = clickCheckerFlag
        clickCheckerFlag = position
        recyclerView?.smoothScrollToPosition(position)
        eventPager?.currentItem = position
    }

}