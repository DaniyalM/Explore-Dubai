package com.dubaiculture.ui.postLogin.events.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItems
import com.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItemsSlots
import com.dubaiculture.utils.dayOfWeek
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo


class ScheduleExpandAdapter(
        private var context: Context,
        nameList: ArrayList<EventScheduleItems>,
        itemNameList: ArrayList<ArrayList<EventScheduleItemsSlots>>,
) : RecyclerView.Adapter<ScheduleExpandAdapter.ViewHolder>() {
    internal var nameList = ArrayList<EventScheduleItems>()
    internal var counter = ArrayList<Int>()
    internal var itemNameList = ArrayList<ArrayList<EventScheduleItemsSlots>>()
    init {
        this.nameList = nameList
        this.itemNameList = itemNameList
        for (i in 0 until nameList.size) {
            counter.add(0)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var day = itemView.findViewById<TextView>(R.id.tv_weekdays)
        var date = itemView.findViewById<TextView>(R.id.tv_date_schedule)
        var monthYear = itemView.findViewById<TextView>(R.id.tv_month_year_schedule)
        var imgToggle = itemView.findViewById<ImageView>(R.id.imgToggle)
        var ll_header = itemView.findViewById<LinearLayout>(R.id.ll_header)
        var innerRecycler=  itemView.findViewById<RecyclerView>(R.id.innerRecyclerView)
        var lineSchedule = itemView.findViewById<ImageView>(R.id.lines_schedule)

//        init {
//            innerRecycler = itemView.findViewById(R.id.innerRecyclerView)
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.schedule_collapse, parent, false)
        val vh: ScheduleExpandAdapter.ViewHolder = ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.day.text = dayOfWeek(nameList[position].date, "EEEE")
        holder.date.text = "${dayOfWeek(nameList[position].date, "dd")}"
        holder.monthYear.text = "${dayOfWeek(nameList[position].date, "MMM").toUpperCase()},${dayOfWeek(nameList[position].date, "yy")}"
        holder.imgToggle.setImageResource(R.drawable.plus)
        if(position == itemCount -1){
            holder.lineSchedule.visibility = View.GONE
        }
        val itemInnerRecyclerView = ScheduleInnerRecyclerviewAdapter(itemNameList[position])
        holder.innerRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        holder.ll_header.setOnClickListener {

            try {
                if (counter[position] % 2 == 0) {
                    YoYo.with(Techniques.BounceInDown)
                        .duration(1000)
                        .playOn(it)
                    holder.imgToggle.setImageResource(R.drawable.remove)
                    holder.innerRecycler.visibility = View.VISIBLE
                } else {
                    holder.innerRecycler.visibility = View.GONE
                    holder.imgToggle.setImageResource(R.drawable.plus)
                    YoYo.with(Techniques.BounceInUp)
                        .duration(1000)
                        .playOn(it)
                }
                counter.set(position, counter[position] + 1)
            }catch (e: IndexOutOfBoundsException){
                e.stackTrace
            }
        }
        holder.innerRecycler.adapter = itemInnerRecyclerView
    }

    override fun getItemCount(): Int {
        return nameList.size
    }


}