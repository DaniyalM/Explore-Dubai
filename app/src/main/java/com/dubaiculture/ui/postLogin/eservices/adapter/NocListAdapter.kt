package com.dubaiculture.ui.postLogin.eservices.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.*
import com.dubaiculture.ui.postLogin.eservices.FieldsType
import com.dubaiculture.ui.postLogin.eservices.FieldsTypeMode
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener
import com.dubaiculture.ui.postLogin.eservices.adapter.viewholders.*
import com.dubaiculture.utils.AppConfigUtils
import com.dubaiculture.utils.AppConfigUtils.setAnimation

class NocListAdapter(val fieldListener: FieldListener) :
    ListAdapter<GetFieldValueItem, BaseFieldViewHolder>(
        object : DiffUtil.ItemCallback<GetFieldValueItem>() {
            override fun areItemsTheSame(oldItem: GetFieldValueItem, newItem: GetFieldValueItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GetFieldValueItem,
                newItem: GetFieldValueItem
            ) =
                oldItem.hashCode() == newItem.hashCode()

        }
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseFieldViewHolder {
        return when (viewType) {
            FieldsType.TIME.id -> {
                TimeFieldViewHolder(
                    EserviceTimeFieldItemCellBinding.inflate(
                        LayoutInflater.from(parent.context)
                    ),
                    fieldListener
                )
            }
            FieldsType.DROP_DOWN.id -> {
                DropDownFieldViewHolder(
                    EserviceDropDownFieldItemCellBinding.inflate(
                        LayoutInflater.from(parent.context)
                    ),
                    fieldListener
                )
            }
            FieldsType.DATE.id -> {
                DateFieldViewHolder(
                    EserviceDateFieldItemCellBinding.inflate(
                        LayoutInflater.from(parent.context)
                    ),
                    fieldListener
                )
            }
            FieldsType.INPUT_TEXT_MULTILINE.id -> {
                MultiLineInputFieldViewHolder(
                    EserviceMultilineInputFieldItemCellBinding.inflate(
                        LayoutInflater.from(parent.context)
                    ),
                    fieldListener
                )
            }
            FieldsType.LABEL.id -> {
                LabelFieldViewHolder(
                    EserviceLabelFieldItemCellBinding.inflate(
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
          setAnimation(holder.itemView.rootView, holder.itemView.rootView.context)
            holder.bind(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.apply {
            return if (FieldsTypeMode.fromName(fieldType).id != 0) {
                FieldsType.fromName(valueType).id
            } else {
                FieldsType.LABEL.id
            }

        }
        return 1
    }
}