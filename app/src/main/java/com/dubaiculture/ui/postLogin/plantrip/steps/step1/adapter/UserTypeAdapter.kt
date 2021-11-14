package com.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.UsersType
import com.dubaiculture.databinding.ItemTripStep1Binding
import com.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter.clicklisteners.UserTypeClickListener

class UserTypeAdapter(val rowClickListener: UserTypeClickListener) :
    ListAdapter<UsersType, UserTypeAdapter.UserTypeViewHolder>(UserTypeAdapter.UserTypeDiffCallback()) {

    inner class UserTypeViewHolder(
        val binding: ItemTripStep1Binding,
        val rowClickListener: UserTypeClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userType: UsersType) {
            binding.userType = userType

            when (userType.checked) {
                true -> {
                    binding.cvUser.strokeColor =
                        ContextCompat.getColor(binding.root.context, R.color.purple_650)
                }
                false -> {
                    binding.cvUser.strokeColor =
                        ContextCompat.getColor(binding.root.context, R.color.transparent)
                }
            }
            binding.cvUser.setOnClickListener {
                rowClickListener.rowClickListener(userType)
                rowClickListener.rowClickListener(userType, absoluteAdapterPosition)
            }
        }
    }

    class UserTypeDiffCallback : DiffUtil.ItemCallback<UsersType>() {
        override fun areItemsTheSame(
            oldItem: UsersType,
            newItem: UsersType
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: UsersType,
            newItem: UsersType
        ): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTypeViewHolder {
        return UserTypeViewHolder(
            ItemTripStep1Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

    override fun onBindViewHolder(holder: UserTypeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}