package com.dubaiculture.ui.postLogin.eservices.adapter.viewholders

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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
        binding.text.id=fieldValue.id
        binding.text.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                fieldListener.fetchInput(
                    fieldValue.copy(selectedValue = s.toString().trim())
                )
            }
        })
    }
}