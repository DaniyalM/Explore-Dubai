package com.dubaiculture.ui.postLogin.popular_service.adapter

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.data.repository.popular_service.local.models.ServiceProcedure
import com.dubaiculture.databinding.ItemsServiceDetailProcedureLayoutBinding


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

        fun bind(serviceProcedure: ServiceProcedure) {
            binding.procedure = serviceProcedure
            binding.tvTitle.movementMethod = ScrollingMovementMethod()
            binding.tvDesc.movementMethod = ScrollingMovementMethod()


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceProcedureViewHolder {
        if (!::mLayoutInflater.isInitialized) {
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
        }
    }


}