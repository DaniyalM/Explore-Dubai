package com.dubaiculture.ui.postLogin.eservices.adapter.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem

abstract class BaseFieldViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {
    abstract fun bind(fieldValue:GetFieldValueItem)
}