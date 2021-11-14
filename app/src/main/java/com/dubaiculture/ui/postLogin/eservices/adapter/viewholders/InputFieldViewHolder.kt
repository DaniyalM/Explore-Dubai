package com.dubaiculture.ui.postLogin.eservices.adapter.viewholders

import com.dubaiculture.databinding.EserviceInputFieldItemBinding
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener

class InputFieldViewHolder(
    val binding: EserviceInputFieldItemBinding,
    val fieldListener: FieldListener
) :
    BaseFieldViewHolder(binding.root) {


    fun onTextChange(s: CharSequence, start: Int, before: Int, count: Int) {
        fieldListener.fetchInput(s.toString())
    }

    override fun bind() {
        binding.fieldClass = this

    }
}