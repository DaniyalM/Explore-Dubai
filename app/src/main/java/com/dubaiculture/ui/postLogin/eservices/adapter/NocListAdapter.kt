package com.dubaiculture.ui.postLogin.eservices.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dubaiculture.databinding.DropDownFieldItemCellBinding
import com.dubaiculture.databinding.EserviceInputFieldItemBinding
import com.dubaiculture.ui.postLogin.eservices.FieldsType
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener
import com.dubaiculture.ui.postLogin.eservices.adapter.viewholders.BaseFieldViewHolder
import com.dubaiculture.ui.postLogin.eservices.adapter.viewholders.DropDownFieldViewHolder
import com.dubaiculture.ui.postLogin.eservices.adapter.viewholders.InputFieldViewHolder

class NocListAdapter(val fieldListener: FieldListener) : ListAdapter<String, BaseFieldViewHolder>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem.contains(newItem)

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem.hashCode() == newItem.hashCode()

    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseFieldViewHolder {
        return when (viewType) {
            FieldsType.INPUT_TEXT.id -> {
                InputFieldViewHolder(
                    EserviceInputFieldItemBinding.inflate(
                        LayoutInflater.from(parent.context)
                    ),
                    fieldListener
                )
            }
            FieldsType.DROP_DOWN.id -> {
                DropDownFieldViewHolder(
                    DropDownFieldItemCellBinding.inflate(
                        LayoutInflater.from(parent.context)
                    ),
                    fieldListener
                )
            }
            else -> InputFieldViewHolder(
                EserviceInputFieldItemBinding.inflate(
                    LayoutInflater.from(parent.context)
                ),
                fieldListener
            )

        }
    }

    override fun onBindViewHolder(holder: BaseFieldViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.apply {
            return FieldsType.fromName(this).id
        }
        return 1
    }
}