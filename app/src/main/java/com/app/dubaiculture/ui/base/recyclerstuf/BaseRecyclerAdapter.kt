package com.app.dubaiculture.ui.base.recyclerstuf

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.bumptech.glide.RequestManager
import com.rishabhharit.roundedimageview.RoundedImageView

abstract class BaseRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val diffCallback = object : DiffUtil.ItemCallback<BaseModel>() {
        override fun areItemsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    protected val differ = AsyncListDiffer(this, diffCallback)



}