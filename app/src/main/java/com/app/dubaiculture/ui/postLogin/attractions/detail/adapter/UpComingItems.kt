package com.app.dubaiculture.ui.postLogin.attractions.detail.adapter

import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.attraction_detail_up_coming_items.view.*

data class UpComingItems(
     val free: String,
    val imgBg :Int,
    val startDate: String,
    val startMonth: String,
    val endDate: String,
    val endMonth: String,
    val category: String,
    val title: String,
    val location: String,
):BaseAdapter(R.layout.attraction_detail_up_coming_items) {
    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            root?.let {
                    it.type_free.text = free
                    it.img_upcoming_bg.setBackgroundResource(imgBg)
                    it.tv_start_date_event.text =startDate
                    it.tv_start_month_event.text =startMonth
                    it.tv_end_date_event.text = endDate
                    it.tv_end_month_event.text =endMonth
                    it.tv_workshop.text =category
                    it.tv_title.text =title
                    it.tv_location.text =location

            }
        }
    }
}