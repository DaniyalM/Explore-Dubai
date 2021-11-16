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
    private lateinit var fieldValueItem: GetFieldValueItem


    fun onTextChange(s: CharSequence, start: Int, before: Int, count: Int) {
        EndTypingWatcher {
            fieldListener.fetchInput(fieldValueItem.copy(selectedValue = s.toString()))
        }

    }


    override fun bind(fieldValue: GetFieldValueItem) {
        fieldValueItem=fieldValue
        binding.fieldClass = this
        binding.data = fieldValue
    }
}
