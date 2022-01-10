package com.dubaiculture.ui.postLogin.popular_service.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.EService
import com.dubaiculture.databinding.ItemsServiceListingLayoutBinding
import com.dubaiculture.ui.postLogin.popular_service.adapter.clicklistener.ServiceClickListner
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show

class PopularServiceListAdapter(val serviceClickListner: ServiceClickListner) :
    ListAdapter<EService, PopularServiceListAdapter.PopularServiceListViewHolder>(object :
        DiffUtil.ItemCallback<EService>() {
        override fun areItemsTheSame(oldItem: EService, newItem: EService): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EService, newItem: EService): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }) {
    inner class PopularServiceListViewHolder(
        val binding: ItemsServiceListingLayoutBinding,
        val serviceClickListner: ServiceClickListner
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(eService: EService) {
            binding.apply {
                setAnimation(rootView, root.context)
                serviceListing = eService
                if(eService.startServiceUrl.isNullOrEmpty()){
                    startService.hide()
                }else{
                    startService.show()
                }

                startService.setOnClickListener {
                    serviceClickListner.onUrlClick(eService)
                }

                viewDetail.setOnClickListener {
                    serviceClickListner.onServiceClick(eService)
                }
            }
        }
    }

    private fun setAnimation(viewToAnimate: View, context: Context) {
        // If the bound view wasn't previously displayed on screen, it's animated
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        viewToAnimate.startAnimation(animation)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = PopularServiceListViewHolder(
        ItemsServiceListingLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
        serviceClickListner = serviceClickListner
    )

    override fun onBindViewHolder(holder: PopularServiceListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}