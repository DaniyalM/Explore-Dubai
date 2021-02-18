package com.app.dubaiculture.ui.base.recyclerstuf

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

abstract class BaseAdapter(private val resLayout: Int) : Item<GroupieViewHolder>() {

    override fun getLayout() = resLayout

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        initBinding(viewHolder, position)
    }

    protected abstract fun initBinding(viewHolder: GroupieViewHolder, position: Int)

    private val diffCallback = object : DiffUtil.ItemCallback<BaseModel>() {
        override fun areItemsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

}