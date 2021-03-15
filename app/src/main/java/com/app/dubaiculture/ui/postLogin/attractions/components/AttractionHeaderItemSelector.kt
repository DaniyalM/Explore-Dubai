package com.app.dubaiculture.ui.postLogin.attractions.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionHeaderItems
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionPagerAdaper
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionHeaderClick
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class AttractionHeaderItemSelector(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs), AttractionHeaderClick {
    private var selectedTextColor: Int? = null
    private var unSelectedTextColor: Int? = null
    private var selectedBackground: Int? = null
    private var unSelectedBackground: Int? = null
    private var selectedInnerImg: Int? = null
    private var unSelectedInnerImg: Int? = null
    private val groupAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var list: List<AttractionCategory>? = null
    private var attractionPager: ViewPager2? = null
    private var recyclerView: RecyclerView? = null

//    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    companion object {
        var clickCheckerFlag: Int = 0
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
//            LinearSnapHelper().attachToRecyclerView(it)

        }
    }

    @JvmName("attractionHeaders")
    fun initialize(
        list: List<AttractionCategory>,
        attractionPager: ViewPager2? = null,
    ) {
        this.list = list
        this.attractionPager = attractionPager


        if (groupAdapter.itemCount == 0) {
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
                    AttractionHeaderItems(
                        displayValue = model.title!!,
                        data = list,
                        isSelected = isSelected,
                        selectedTextColor = selectedTextColor,
                        unSelectedTextColor = unSelectedTextColor,
                        selectedBackground = getDrawableFromId(selectedBackground),
                        unSelectedBackground = getDrawableFromId(unSelectedBackground),
                        selectedInnerImg = model.selectedSvg,
                        unSelectedInnerImg = model.icon,
                        progressListener = this
                    )
                )
            }
        } else {
            list?.get(previousPosition)?.let {
                groupAdapter.notifyItemChanged(previousPosition, AttractionHeaderItems(
                    displayValue = it.title!!,
                    data = list,
                    isSelected = isSelected,
                    selectedTextColor = selectedTextColor,
                    unSelectedTextColor = unSelectedTextColor,
                    selectedBackground = getDrawableFromId(selectedBackground),
                    unSelectedBackground = getDrawableFromId(unSelectedBackground),
                    selectedInnerImg = it.selectedSvg,
                    unSelectedInnerImg = it.icon,
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
        previousPosition = clickCheckerFlag
        positionUpdate(position)
        itemsAddnUpdation(true)
    }

    fun positionUpdate(position: Int) {
        clickCheckerFlag = position
        recyclerView?.smoothScrollToPosition(position)
        attractionPager?.currentItem = position

    }


}