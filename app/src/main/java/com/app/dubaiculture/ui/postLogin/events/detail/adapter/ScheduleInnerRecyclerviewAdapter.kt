package com.app.dubaiculture.ui.postLogin.events.detail.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.remote.response.EventScheduleItemsTimeSlotsDTO
import kotlinx.android.synthetic.main.schedule_expand.view.*


class ScheduleInnerRecyclerviewAdapter(var nameList : ArrayList<EventScheduleItemsTimeSlotsDTO>) :
    RecyclerView.Adapter<ScheduleInnerRecyclerviewAdapter.ViewHolder>() {
//    var nameList = ArrayList<String>()

    init {
        this.nameList = nameList
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var summary = itemView.tv_summary
        var time: TextView
        init {
            time = itemView.findViewById(R.id.tv_start_time)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.schedule_expand, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.time.text = nameList[position].timeFrom
    }

    override fun getItemCount(): Int {
        return nameList.size
    }


}