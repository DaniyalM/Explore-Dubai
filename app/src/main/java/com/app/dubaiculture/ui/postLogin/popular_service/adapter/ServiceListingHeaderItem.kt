package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.AttractionTitleListItemBinding
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.TabsHeaderClick
import com.app.dubaiculture.ui.postLogin.popular_service.components.ServicesHeaderItemSelector
import com.app.dubaiculture.ui.postLogin.popular_service.components.ServicesListingHeaderItemSelector.Companion.SERVICE_CLICK_CHECKER_FLAG
import com.google.android.material.card.MaterialCardView
import com.xwray.groupie.databinding.BindableItem

class ServiceListingHeaderItem<T>(
    val displayValue: String,
    val data: T,
    var isSelected: Boolean = false,
    private val selectedInnerImg: String? = null,
    private val unSelectedInnerImg: String? = null,
    private val progressListener: TabsHeaderClick? = null,
    private val colorBg: String? = null,
    private val resLayout: Int = R.layout.attraction_title_list_item
) : BindableItem<AttractionTitleListItemBinding>() {


    override fun getLayout() = resLayout
    override fun bind(viewBinding: AttractionTitleListItemBinding, position: Int) {
        val title = viewBinding.tvTitle
        val image = viewBinding.imgInnerIcon
        val card = viewBinding.cardview
        title.text = displayValue
        isSelected = SERVICE_CLICK_CHECKER_FLAG == position
        renderSelection(title, image, card)
        viewBinding.root.setOnClickListener {
            progressListener?.onClick(position)
            if (SERVICE_CLICK_CHECKER_FLAG == position) {
                isSelected = true
                renderSelection(title, image, card)
            }

        }
    }

    private fun renderSelection(
        textView: TextView, imgInner: ImageView,
        view: MaterialCardView,
    ) {
        if (isSelected) {
            view.setCardBackgroundColor(Color.parseColor(colorBg))
            textView.setTextColor(ContextCompat.getColor(view.context, R.color.white_900))
            selectedInnerImg?.let { drawable ->
//                imgInner.glideInstance(drawable, true).into(imgInner)
//                imgInner.setImageDrawable(drawable)
                imgInner.setColorFilter(ContextCompat.getColor(view.context, R.color.white_900))
            }

        } else {
            view.setCardBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.white_900
                )
            )
            textView.setTextColor(ContextCompat.getColor(view.context, R.color.gray_700))
            unSelectedInnerImg?.let { drawable ->
//                imgInner.glideInstance(drawable, true).into(imgInner)
//                imgInner.setImageDrawable(drawable)
                imgInner.setColorFilter(ContextCompat.getColor(view.context, R.color.purple_900))
            }
        }

    }
}