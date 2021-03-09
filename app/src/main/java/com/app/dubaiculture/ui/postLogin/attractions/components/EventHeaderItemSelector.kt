package com.app.dubaiculture.ui.postLogin.attractions.components

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
import com.app.dubaiculture.ui.postLogin.events.adapters.EventHeaderItems
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class EventHeaderItemSelector (context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs), AttractionHeaderClick {
    private var selectedTextColor: Int? = null
    private var unSelectedTextColor: Int? = null
    private var selectedBackground: Int? = null
    private var unSelectedBackground: Int? = null
    private var selectedInnerImg: Int? = null
    private var unSelectedInnerImg: Int? = null
    private val groupAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var list: List<EventHomeListing>? = null
    private var eventPager: ViewPager2? = null
    private  var recyclerView: RecyclerView?=null

    companion object {
        var clickCheckerFlag: Int = 0
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
        recyclerView= view.findViewById(R.id.rVgeneric)

        recyclerView?.let {
            it.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            addView(view)
            it.adapter = groupAdapter
//            LinearSnapHelper().attachToRecyclerView(it)

        }
    }

    @JvmName("eventHeader")
    fun initialize(
        list: List<EventHomeListing>,
        attractionPager: ViewPager2? = null
    ) {
        this.list = list
        this.eventPager = attractionPager
        itemsAddnUpdation(list)
    }

    fun itemsAddnUpdation(
        list: List<EventHomeListing>,
        isUpdate: Boolean = false,
    ) {

        list.forEachIndexed { index, model ->
            var isSelected = false
            if (EventHeaderItemSelector.clickCheckerFlag == index){
                isSelected = true
                positionUpdate(EventHeaderItemSelector.clickCheckerFlag)
            }


            if (!isUpdate) {
                groupAdapter.add(
                    EventHeaderItems(
                        displayValue ="",
                        data = list,
                        isSelected = isSelected,
                        selectedTextColor = selectedTextColor,
                        unSelectedTextColor = unSelectedTextColor,
                        selectedBackground = getDrawableFromId(selectedBackground),
                        unSelectedBackground = getDrawableFromId(unSelectedBackground),
//                        selectedInnerImg = getDrawableFromId(model.imgSelected.toInt()),
//                        unSelectedInnerImg = getDrawableFromId(model.imgUnSelected.toInt()),
                        progressListener = this
                    )
                )
            } else {

                groupAdapter.notifyItemChanged(index, EventHeaderItems(
                    displayValue = model.events[index].title!!,
                    data = list,
                    isSelected = isSelected,
                    selectedTextColor = selectedTextColor,
                    unSelectedTextColor = unSelectedTextColor,
                    selectedBackground = getDrawableFromId(selectedBackground),
                    unSelectedBackground = getDrawableFromId(unSelectedBackground),
//                    selectedInnerImg = getDrawableFromId(model.imgSelected.toInt()),
//                    unSelectedInnerImg = getDrawableFromId(model.imgUnSelected.toInt()),
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
        list?.let { itemsAddnUpdation(it, true) }
    }

    fun positionUpdate(position: Int){
       clickCheckerFlag = position
        recyclerView?.smoothScrollToPosition(position)
        eventPager?.currentItem = clickCheckerFlag
    }

}