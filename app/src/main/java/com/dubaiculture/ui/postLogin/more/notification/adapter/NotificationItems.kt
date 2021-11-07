package com.dubaiculture.ui.postLogin.more.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.data.repository.more.remote.response.notification.Notifications
import com.dubaiculture.databinding.ItemsNotitifcationsLayoutBinding

class NotificationItems(val notificationCounts: NotificationCounts) :
    PagingDataAdapter<Notifications, NotificationItems.NotificationViewHolder>(
        NotificationDiffCallback()
    ) {

    inner class NotificationViewHolder(
        val binding: ItemsNotitifcationsLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notifications: Notifications) {
            binding.notification = notifications
            notificationCounts.getNotificationCount(itemCount)


        }
    }

    class NotificationDiffCallback : DiffUtil.ItemCallback<Notifications>() {
        override fun areItemsTheSame(
            oldItem: Notifications,
            newItem: Notifications
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Notifications,
            newItem: Notifications
        ): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(
            ItemsNotitifcationsLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    interface NotificationCounts {
        fun getNotificationCount(count: Int)
    }
}