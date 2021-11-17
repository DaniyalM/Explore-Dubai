package com.dubaiculture.ui.postLogin.eservices.adapter.viewholders

import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.EserviceInputFieldItemBinding
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener

class InputFieldViewHolder(
    val binding: EserviceInputFieldItemBinding,
    val fieldListener: FieldListener
) :
    BaseFieldViewHolder(binding.root) {
    override fun bind(fieldValue: GetFieldValueItem) {
        binding.fieldClass = this
        binding.data = fieldValue

        binding.text.setOnClickListener {
            fieldListener.fetchInput(
                fieldValue
            )
        }


    }
}