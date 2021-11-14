package com.dubaiculture.ui.postLogin.eservices.adapter.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseFieldViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {
    abstract fun bind()
}