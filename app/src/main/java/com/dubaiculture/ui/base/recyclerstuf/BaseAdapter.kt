package com.dubaiculture.ui.base.recyclerstuf

import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.dubaiculture.data.repository.explore.local.models.BaseModel
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

abstract class BaseAdapter(private val resLayout: Int) : Item<GroupieViewHolder>() {

    override fun getLayout() = resLayout

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        initBinding(viewHolder, position)
    }

    protected abstract fun initBinding(viewHolder: GroupieViewHolder, position: Int)


}