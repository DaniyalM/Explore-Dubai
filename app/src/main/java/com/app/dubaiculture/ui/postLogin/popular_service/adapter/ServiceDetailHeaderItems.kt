package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.ItemLayoutTabHeaderBinding
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionHeaderClick
import com.app.dubaiculture.utils.AppConfigUtils.SERVICE_DETAIL_HEADER_FLAG
import com.xwray.groupie.databinding.BindableItem

class ServiceDetailHeaderItems(
    val displayValue: String,
    var isSelected: Boolean = false,
    private val progressListener: AttractionHeaderClick? = null,

    ) : BindableItem<ItemLayoutTabHeaderBinding>() {
    override fun bind(viewBinding: ItemLayoutTabHeaderBinding, position: Int) {

        viewBinding.headerTitle.text = displayValue
        isSelected = SERVICE_DETAIL_HEADER_FLAG == position

        renderSelection(viewBinding.headerTitle, viewBinding.highlighter)
        viewBinding.headerTitle.setOnClickListener {
            progressListener?.onClick(position)
            if (SERVICE_DETAIL_HEADER_FLAG == position) {
                isSelected = true
                renderSelection(viewBinding.headerTitle, viewBinding.highlighter)
            }

        }
    }

    override fun getLayout() = R.layout.item_layout_tab_header

    private fun renderSelection(
        textView: TextView,
        highlighter: View,
    ) {
        if (isSelected) {
            highlighter.visibility = View.VISIBLE
            textView.setTextColor(ContextCompat.getColor(highlighter.context, R.color.purple_600))
        } else {
            highlighter.visibility = View.GONE
            textView.setTextColor(ContextCompat.getColor(highlighter.context, R.color.gray_700))
        }

    }
}