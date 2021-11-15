package com.dubaiculture.ui.postLogin.eservices.adapter.viewholders

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import com.dubaiculture.R
import com.dubaiculture.databinding.DropDownFieldItemCellBinding
import com.dubaiculture.ui.postLogin.eservices.adapter.listeners.FieldListener

class DropDownFieldViewHolder(
    val binding: DropDownFieldItemCellBinding,
    val fieldListener: FieldListener
) : BaseFieldViewHolder(binding.root) {


    fun onDropDownClicked(view: View) {
        showMenu(view, R.menu.duration_menu)
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(binding.root.context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            fieldListener.dropDownValue(menuItem.title.toString())
            binding.dropdown.text = menuItem.title.toString()

            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    override fun bind() {
        binding.fieldClass = this
    }
}