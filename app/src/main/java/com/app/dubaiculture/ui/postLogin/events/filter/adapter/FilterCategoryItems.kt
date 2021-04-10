package com.app.dubaiculture.ui.postLogin.events.filter.adapter

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Filter
import com.app.dubaiculture.data.repository.filter.Categories
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.item_category_filter.view.*

class FilterCategoryItems (private val categories : Filter, private var isSelected: Boolean = false ):
    BaseAdapter(R.layout.item_category_filter) {
    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            root?.let {
                it.tv_title_category.text = categories.title
                if(categories.isSelected){
                    it.borderBg.setBackgroundResource(R.drawable.rounded_shape_area_exp_purple)
                    it.tv_title_category.setTextColor(Color.parseColor("#FFFFFF"))
                }else{
                    it.borderBg.setBackgroundResource(R.drawable.rounded_shape_area_exp)
                    it.tv_title_category.setTextColor(Color.parseColor("#2A2D31"))
                }
                    root.root_item_selection.setOnClickListener {
                        categories.isSelected = !categories.isSelected
                        if (categories.isSelected) {
                            root.borderBg.setBackgroundResource(R.drawable.rounded_shape_area_exp_purple)
                            it.tv_title_category.setTextColor(Color.parseColor("#FFFFFF"))
                        } else
                            root.borderBg.setBackgroundResource(R.drawable.rounded_shape_area_exp)
                        it.tv_title_category.setTextColor(Color.parseColor("#2A2D31"))
                        notifyChanged()
                    }
                }
            }
        }
    }
