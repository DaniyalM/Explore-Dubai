package com.dubaiculture.ui.postLogin.eservices.adapter.viewholders

import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.EserviceMultilineInputFieldItemCellBinding
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener
import com.dubaiculture.utils.EndTypingWatcher

class MultiLineInputFieldViewHolder(
    val binding: EserviceMultilineInputFieldItemCellBinding,
    val fieldListener: FieldListener
) :
    BaseFieldViewHolder(binding.root) {
    override fun bind(fieldValue: GetFieldValueItem) {
        binding.data = fieldValue
        binding.text.setOnClickListener {
            fieldListener.fetchInput(
                fieldValue
            )
        }
    }
}
