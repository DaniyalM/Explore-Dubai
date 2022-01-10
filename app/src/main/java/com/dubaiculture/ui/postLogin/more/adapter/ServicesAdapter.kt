package com.dubaiculture.ui.postLogin.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.ServiceCategory
import com.dubaiculture.databinding.ItemsMoreLayoutBinding
import com.dubaiculture.ui.postLogin.more.adapter.clicklisteners.ServicesClickListener
import com.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter.clicklisteners.UserTypeClickListener

class ServicesAdapter(val rowClickListener: ServicesClickListener, val glide: RequestManager? = null) :
    ListAdapter<ServiceCategory, ServicesAdapter.ServicesViewHolder>(ServicesAdapter.ServicesDiffCallback()) {

    inner class ServicesViewHolder(
        val binding: ItemsMoreLayoutBinding,
        val rowClickListener: ServicesClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(service: ServiceCategory) {

            binding.tvTitle.text = service.title
            glide!!.load(BuildConfig.BASE_URL + service?.normalIcon).placeholder(R.drawable.museum_more).into(binding.img)

            binding.rootView.setOnClickListener {
                rowClickListener.rowClickListener(service)
                rowClickListener.rowClickListener(service, absoluteAdapterPosition)
            }
        }
    }

    class ServicesDiffCallback : DiffUtil.ItemCallback<ServiceCategory>() {
        override fun areItemsTheSame(
            oldItem: ServiceCategory,
            newItem: ServiceCategory
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ServiceCategory,
            newItem: ServiceCategory
        ): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        return ServicesViewHolder(
            ItemsMoreLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}