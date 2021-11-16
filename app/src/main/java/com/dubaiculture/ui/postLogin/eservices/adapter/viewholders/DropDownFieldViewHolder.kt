package com.dubaiculture.ui.postLogin.eservices.adapter.viewholders

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import com.dubaiculture.R
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.EserviceDropDownFieldItemCellBinding
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener

class DropDownFieldViewHolder(
    val binding: EserviceDropDownFieldItemCellBinding,
    val fieldListener: FieldListener
) : BaseFieldViewHolder(binding.root) {
    private lateinit var fieldValueItem: GetFieldValueItem

    fun onDropDownClicked(view: View) {
        showMenu(view, R.menu.eservice_menu)
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(binding.root.context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        fieldValueItem.fieldValue.forEach {
            popup.menu.add(it.optionValues)
        }


        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            fieldListener.dropDownValue(fieldValueItem.copy(selectedValue = menuItem.title.toString()))
            binding.dropdown.text = menuItem.title.toString()

            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }


    override fun bind(fieldValue: GetFieldValueItem) {
        fieldValueItem = fieldValue
        binding.dropdown.text = fieldValue.selectedValue?:fieldValue.english
        binding.fieldClass = this
    }
}