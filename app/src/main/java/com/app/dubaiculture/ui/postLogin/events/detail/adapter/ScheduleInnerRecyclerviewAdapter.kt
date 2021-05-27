package com.app.dubaiculture.ui.postLogin.events.detail.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItemsSlots
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.schedule_expand.view.*
import java.util.*


class ScheduleInnerRecyclerviewAdapter(var nameList: ArrayList<EventScheduleItemsSlots>) :
    RecyclerView.Adapter<ScheduleInnerRecyclerviewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time = itemView.tv_start_time
        val summary = itemView.tv_summary
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