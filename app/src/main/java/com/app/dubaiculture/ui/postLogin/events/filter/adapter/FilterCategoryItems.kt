package com.app.dubaiculture.ui.postLogin.events.filter.adapter

import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Filter
import com.app.dubaiculture.data.repository.news.local.NewsTags
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.app.dubaiculture.utils.getColorFromAttr
import com.google.android.flexbox.FlexboxLayout
import com.xwray.groupie.GroupieViewHolder

class FilterCategoryItems <T>(private val categories : T, private var isSelected: Boolean = false ):
    BaseAdapter(R.layout.item_category_filter) {
    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            root?.let {
                if (categories is Filter){
                    it.findViewById<TextView>(R.id.tv_title_category).text = categories.title
                    if(categories.isSelected){
                        it.findViewById<FlexboxLayout>(R.id.borderBg).setBackgroundResource(R.drawable.rounded_shape_area_exp_purple)
                        it.findViewById<TextView>(R.id.tv_title_category).setTextColor(Color.parseColor("#FFFFFF"))
                    }else{
                        it.findViewById<FlexboxLayout>(R.id.borderBg)
                            .setBackgroundResource(R.drawable.rounded_shape_area_exp)
                        it.findViewById<TextView>(R.id.tv_title_category)
                            .setTextColor(it.context.getColorFromAttr(R.attr.colorSecondary))
                    }
                    root.findViewById<LinearLayout>(R.id.root_item_selection).setOnClickListener {
                        categories.isSelected = !categories.isSelected
                        if (categories.isSelected) {
                            root.findViewById<FlexboxLayout>(R.id.borderBg).setBackgroundResource(R.drawable.rounded_shape_area_exp_purple)
                            it.findViewById<TextView>(R.id.tv_title_category).setTextColor(Color.parseColor("#FFFFFF"))
                        } else
                            root.findViewById<FlexboxLayout>(R.id.borderBg).setBackgroundResource(R.drawable.rounded_shape_area_exp)
                        it.findViewById<TextView>(R.id.tv_title_category).setTextColor(Color.parseColor("#2A2D31"))
                        notifyChanged()
                    }
                }
                if (categories is NewsTags){
                    it.findViewById<TextView>(R.id.tv_title_category).text = categories.tag_title
                    if (categories.copy(isSelected = !categories.isSelected).isSelected) {
                        it.findViewById<FlexboxLayout>(R.id.borderBg)
                            .setBackgroundResource(R.drawable.rounded_shape_area_exp_purple)
                        it.findViewById<TextView>(R.id.tv_title_category)
                            .setTextColor(Color.parseColor("#FFFFFF"))
                    } else {
                        it.findViewById<FlexboxLayout>(R.id.borderBg)
                            .setBackgroundResource(R.drawable.rounded_shape_area_exp)
                        it.findViewById<TextView>(R.id.tv_title_category)
                            .setTextColor(Color.parseColor("#2A2D31"))
                    }
                    root.findViewById<LinearLayout>(R.id.root_item_selection).setOnClickListener {

                        if (categories.copy(isSelected = !categories.isSelected).isSelected) {
                            root.findViewById<FlexboxLayout>(R.id.borderBg)
                                .setBackgroundResource(R.drawable.rounded_shape_area_exp_purple)
                            it.findViewById<TextView>(R.id.tv_title_category)
                                .setTextColor(Color.parseColor("#FFFFFF"))
                        } else
                            root.findViewById<FlexboxLayout>(R.id.borderBg)
                                .setBackgroundResource(R.drawable.rounded_shape_area_exp)
                        it.findViewById<TextView>(R.id.tv_title_category)
                            .setTextColor(Color.parseColor("#2A2D31"))
                        notifyChanged()
                    }
                }

                }
            }
        }
    }
