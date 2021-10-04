package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.data.repository.popular_service.local.models.ServiceProcedure
import com.app.dubaiculture.databinding.ItemsServiceDetailProcedureLayoutBinding
import com.github.vipulasri.timelineview.TimelineView

class ServiceProcedureListAdapter() :
    ListAdapter<ServiceProcedure, ServiceProcedureListAdapter.ServiceProcedureViewHolder>
        (object : DiffUtil.ItemCallback<ServiceProcedure>() {
        override fun areItemsTheSame(
            oldItem: ServiceProcedure,
            newItem: ServiceProcedure
        ) = oldItem.title == newItem.title

        override fun areContentsTheSame(
            oldItem: ServiceProcedure,
            newItem: ServiceProcedure
        ) = oldItem.hashCode() == newItem.hashCode()
    }) {

    inner class ServiceProcedureViewHolder(val binding: ItemsServiceDetailProcedureLayoutBinding,val viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(serviceProcedure: ServiceProcedure) {
            binding.procedure = serviceProcedure
            binding.timeline.initLine(viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceProcedureViewHolder {
        return ServiceProcedureViewHolder(
            ItemsServiceDetailProcedureLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            viewType
        )
    }

    override fun onBindViewHolder(holder: ServiceProcedureViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}