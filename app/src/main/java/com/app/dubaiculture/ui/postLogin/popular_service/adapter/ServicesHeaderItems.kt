package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.TabsHeaderClick
import com.app.dubaiculture.ui.postLogin.popular_service.components.ServicesHeaderItemSelector.Companion.SERVICE_CLICK_CHECKER_FLAG
import com.google.android.material.card.MaterialCardView
import com.xwray.groupie.GroupieViewHolder

class ServicesHeaderItems<T>(
    val displayValue: String,
    val data: T,
    var isSelected: Boolean = false,
    private val selectedInnerImg: Drawable? = null,
    private val unSelectedInnerImg: Drawable? = null,
    private val progressListener: TabsHeaderClick? = null,
    private val colorBg: String? = null
) : BaseAdapter(R.layout.attraction_title_list_item) {
    private lateinit var view: View
    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        view = viewHolder.root
        viewHolder.apply {

            root?.let { it ->
                val title = it.findViewById<TextView>(R.id.tv_title)
                val image = it.findViewById<ImageView>(R.id.imgInnerIcon)
                val card = it.findViewById<MaterialCardView>(R.id.cardview)
                title.text = displayValue
                isSelected = SERVICE_CLICK_CHECKER_FLAG == position
                renderSelection(title, image, card)
                it.setOnClickListener {
                    progressListener?.onClick(position)
                    if (SERVICE_CLICK_CHECKER_FLAG == position) {
                        isSelected = true
                        renderSelection(title, image, card)
                    }

                }


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
                imgInner.setImageDrawable(drawable)
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
                imgInner.setImageDrawable(drawable)
                imgInner.setColorFilter(ContextCompat.getColor(view.context, R.color.purple_900))
            }
        }

    }
}