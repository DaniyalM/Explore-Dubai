package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.ServiceProcedure
import com.app.dubaiculture.databinding.ItemsServiceDetailProcedureLayoutBinding
import com.app.dubaiculture.utils.AppConfigUtils.getDrawable
import com.github.vipulasri.timelineview.TimelineView

class ServiceProcedureListAdapter :
    ListAdapter<ServiceProcedure, ServiceProcedureListAdapter.ServiceProcedureViewHolder>
        (object : DiffUtil.ItemCallback<ServiceProcedure>() {

        override fun areItemsTheSame(
            oldItem: ServiceProcedure,
            newItem: ServiceProcedure
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ServiceProcedure,
            newItem: ServiceProcedure
        ) = oldItem.hashCode() == newItem.hashCode()
    }) {
    private lateinit var mLayoutInflater: LayoutInflater

    inner class ServiceProcedureViewHolder(
        val binding: ItemsServiceDetailProcedureLayoutBinding,
        val viewType: Int
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.timeline.initLine(viewType)
        }

        fun bind(serviceProcedure: ServiceProcedure) {
            binding.procedure = serviceProcedure
        }
    }



    override fun getItemViewType(position: Int)=
        TimelineView.getTimeLineViewType(position, itemCount)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceProcedureViewHolder {
        if(!::mLayoutInflater.isInitialized) {
            mLayoutInflater = LayoutInflater.from(parent.context)
        }

        return ServiceProcedureViewHolder(
            ItemsServiceDetailProcedureLayoutBinding.inflate(
                mLayoutInflater, parent, false
            ),
            viewType
        )
    }

    override fun onBindViewHolder(holder: ServiceProcedureViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
//            setMarker(holder, R.drawable.circle_sitemap, R.color.gray_400)
        }
    }

    private fun setMarker(holder: ServiceProcedureViewHolder, drawableResId: Int, colorFilter: Int) {
        holder.binding.timeline.marker = getDrawable(holder.itemView.context, drawableResId, ContextCompat.getColor(holder.itemView.context, colorFilter))
    }
}