package com.dubaiculture.ui.postLogin.popular_service.adapter

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dubaiculture.R
import com.dubaiculture.databinding.AttractionTitleListItemBinding
import com.dubaiculture.ui.postLogin.attractions.clicklisteners.TabsHeaderClick
import com.dubaiculture.ui.postLogin.popular_service.components.ServicesListingHeaderItemSelector.Companion.SERVICE_CLICK_CHECKER_FLAG
import com.dubaiculture.utils.getColorFromAttr
import com.dubaiculture.utils.glideInstance
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
            textView.setTextColor(
                ContextCompat.getColor(view.context, R.color.white_900)
            )

            selectedInnerImg?.let { drawable ->
                imgInner.glideInstance(drawable, true).into(imgInner)
//                imgInner.setImageDrawable(drawable)
                imgInner.setColorFilter(
                    ContextCompat.getColor(view.context, R.color.white_900)
                )
            }

        } else {
            view.setCardBackgroundColor(
                view.context.getColorFromAttr(R.attr.colorSurface)
            )
            textView.setTextColor(
                ContextCompat.getColor(view.context, R.color.purple_900)
            )
            unSelectedInnerImg?.let { drawable ->
                imgInner.glideInstance(drawable, true).into(imgInner)
//                imgInner.setImageDrawable(drawable)
                imgInner.setColorFilter(ContextCompat.getColor(view.context, R.color.purple_900))
            }
        }

    }
}