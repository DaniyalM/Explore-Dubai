package com.app.dubaiculture.ui.postLogin.events.adapters

import android.view.View
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.app.dubaiculture.utils.setTextColorRes
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.event_detail_schedule_items_layout_header.view.*

class EventScheduleItems(
    private val todate: String,
    private val monthYear: String,
    private val day: String,
    private val startTime: String,
    private val startTimeDesc: String,
    private val endTime: String,
    private val endTimeDsec: String,
) : BaseAdapter(
    R.layout.event_detail_schedule_items_layout_header) {
    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            root?.let {
                it.tv_date_schedule.text = todate
                it.tv_month_year_schedule.text = monthYear
                it.tv_weekdays.text = day
                it.tv_start_time.text = startTime
                it.tv_starttime_desc.text = startTimeDesc
                it.tv_end_time.text = endTime
                it.tv_end_time_desc.text = endTimeDsec
                var isCollapse = false
                it.imgToggle.setOnClickListener { imgToggle->
                    if(isCollapse){
                        isCollapse = false
                        root.tv_weekdays.setTextColorRes(R.color.black_200)
                        YoYo.with(Techniques.BounceInUp)
                            .duration(700)
                            .playOn(imgToggle)
                        root.ll_child.visibility = View.GONE
                        imgToggle.setBackgroundResource(R.drawable.plus)

                    }else{
                        isCollapse = true
                        root.tv_weekdays.setTextColorRes(R.color.purple_900)
                        YoYo.with(Techniques.BounceInDown)
                            .duration(700)
                            .playOn( root.ll_child)
                        root.ll_child.visibility = View.VISIBLE
                        imgToggle.setBackgroundResource(R.drawable.remove)
                    }
                }

            }
        }

    }
}