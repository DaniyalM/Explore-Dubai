package com.dubaiculture.ui.postLogin.events.detail.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItemsSlots
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import java.util.*


class ScheduleInnerRecyclerviewAdapter(var nameList: ArrayList<EventScheduleItemsSlots>) :
    RecyclerView.Adapter<ScheduleInnerRecyclerviewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time = itemView.findViewById<TextView>(R.id.tv_start_time)
        val summary = itemView.findViewById<TextView>(R.id.tv_summary)
        val context = itemView.context
    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val myInflator = LayoutInflater.from(parent.context).inflate(R.layout.schedule_expand,parent,false)
//        return ViewHolder(myInflator)
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.schedule_expand, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        YoYo.with(Techniques.BounceInDown)
            .duration(1000)
            .playOn(holder.itemView)
        holder.time.text = "${nameList[position].timeFrom} - ${nameList[position].timeTo} "
        holder.summary.text = nameList[position].summary
    }

    override fun getItemCount(): Int {
        return nameList.size
    }


}