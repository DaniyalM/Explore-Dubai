package com.app.dubaiculture.ui.postLogin.events.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItems
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItemsSlots
import com.app.dubaiculture.utils.dateFormat
import com.app.dubaiculture.utils.dayOfWeek
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.schedule_collapse.view.*


class ScheduleExpandAdapter(
  internal var context: Context,
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
        holder.day.text = dayOfWeek(nameList[position].date)
        holder.imgToggle.setImageResource(R.drawable.plus)
        val itemInnerRecyclerView = ScheduleInnerRecyclerviewAdapter(itemNameList[position])
        holder.innerRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        holder.imgToggle.setOnClickListener {

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
                counter[position] = counter[position] + 1
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