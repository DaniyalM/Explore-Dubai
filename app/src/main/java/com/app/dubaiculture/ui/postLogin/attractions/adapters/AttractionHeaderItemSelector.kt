package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.ViewPager2
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.Attraction
import com.app.dubaiculture.ui.postLogin.attractions.microservices.AttractionHeaderClick
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.lang.Exception

class AttractionHeaderItemSelector(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs), AttractionHeaderClick {
    private var selectedTextColor: Int? = null
    private var unSelectedTextColor: Int? = null
    private var selectedBackground: Int? = null
    private var unSelectedBackground: Int? = null
    private var selectedInnerImg: Int? = null
    private var unSelectedInnerImg: Int? = null
    private val groupAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var list: List<Attraction>? = null
    private var attractionPager: ViewPager2? = null


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
        val recyclerView = view.findViewById<RecyclerView>(R.id.rVgeneric)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        addView(view)
        recyclerView.adapter = groupAdapter
        LinearSnapHelper().attachToRecyclerView(recyclerView)



    }

    @JvmName("interests")
    fun initialize(
        list: List<Attraction>,
        attractionPager: ViewPager2? = null,
    ) {
        this.list = list
        this.attractionPager = attractionPager

        itemsAddnUpdation(list, attractionPager)
    }

    private fun itemsAddnUpdation(
        list: List<Attraction>,
        attractionPager: ViewPager2? = null,
    ) {

        list.forEachIndexed { index, model ->
            var isSelected = false
            if (clickCheckerFlag == index)
                isSelected = true


            groupAdapter.add(
                AttractionHeaderItems(
                    displayValue = model.title,
                    data = list,
                    isSelected = isSelected,
                    selectedTextColor = selectedTextColor,
                    unSelectedTextColor = unSelectedTextColor,
                    selectedBackground = getDrawableFromId(selectedBackground),
                    unSelectedBackground = getDrawableFromId(unSelectedBackground),
                    selectedInnerImg = getDrawableFromId(model.imgSelected),
                    unSelectedInnerImg = getDrawableFromId(model.imgUnSelected),
                    attractionPager = attractionPager,
                    progressListener = this
                )
            )
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
        clickCheckerFlag = position
        list?.let {
            try {
                Toast.makeText(context,"${groupAdapter.itemCount}",Toast.LENGTH_SHORT).show()
                it.forEachIndexed { index, attraction ->
                    if (clickCheckerFlag!=index){
                        groupAdapter.notifyItemChanged(index, AttractionHeaderItems(
                            displayValue = attraction.title,
                            data = list,
                            isSelected = false,
                            selectedTextColor = selectedTextColor,
                            unSelectedTextColor = unSelectedTextColor,
                            selectedBackground = getDrawableFromId(selectedBackground),
                            unSelectedBackground = getDrawableFromId(unSelectedBackground),
                            selectedInnerImg = getDrawableFromId(attraction.imgSelected),
                            unSelectedInnerImg = getDrawableFromId(attraction.imgUnSelected),
                            attractionPager = attractionPager,
                            progressListener = this)
                        )
                    }

                }

            }catch (ex:IndexOutOfBoundsException){
                groupAdapter.notifyDataSetChanged()
            }




        }


    }


}