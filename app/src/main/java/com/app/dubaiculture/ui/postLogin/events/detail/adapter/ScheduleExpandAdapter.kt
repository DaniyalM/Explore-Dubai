package com.app.dubaiculture.ui.postLogin.events.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.remote.response.EventScheduleItemsDTO
import com.app.dubaiculture.data.repository.event.remote.response.EventScheduleItemsTimeSlotsDTO
import kotlinx.android.synthetic.main.schedule_collapse.view.*


class ScheduleExpandAdapter(
    var context: Context,
    var nameList: ArrayList<EventScheduleItemsDTO>,
    var itemNameList: ArrayList<ArrayList<EventScheduleItemsTimeSlotsDTO>>,
) : RecyclerView.Adapter<ScheduleExpandAdapter.ViewHolder>() {
//    var nameList = ArrayList<String>()
//    var image = ArrayList<String>()
    var counter = ArrayList<Int>()
//    internal var itemNameList = ArrayList<ArrayList<String>>()

    //    var context: Context
    init {
//        this.nameList = nameList
//        this.itemNameList = itemNameList
//        this.context = context
        for (i in 0 until nameList.size) {
            counter.add(0)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var day = itemView.tv_weekdays
        var date = itemView.tv_date_schedule
        var monthYear = itemView.tv_month_year_schedule
        var imgToggle = itemView.imgToggle
        var innerRecycler: RecyclerView

        init {
            innerRecycler = itemView.findViewById(R.id.innerRecyclerView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.schedule_collapse, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.day.text = nameList[position].date
        val itemInnerRecyclerView = ScheduleInnerRecyclerviewAdapter(itemNameList[position])
        holder.innerRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        holder.imgToggle.setOnClickListener {
            if (counter[position] % 2 == 0) {
                holder.innerRecycler.visibility = View.VISIBLE
            } else {
                holder.innerRecycler.visibility = View.GONE
            }
            counter[position] = counter[position] + 1
        }
        holder.innerRecycler.adapter = itemInnerRecyclerView
    }

    override fun getItemCount(): Int {
        return nameList.size
    }


}