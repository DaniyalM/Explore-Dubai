package com.dubaiculture.ui.postLogin.eservices.adapter.viewholders

import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.EserviceLabelFieldItemCellBinding
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener

class LabelFieldViewHolder(
    val binding: EserviceLabelFieldItemCellBinding,
    val fieldListener: FieldListener
) :
    BaseFieldViewHolder(binding.root) {

    override fun bind(fieldValue: GetFieldValueItem) {
        binding.data = fieldValue
    }

}