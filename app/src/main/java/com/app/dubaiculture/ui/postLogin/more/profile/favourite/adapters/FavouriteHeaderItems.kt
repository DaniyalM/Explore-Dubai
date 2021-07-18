package com.app.dubaiculture.ui.postLogin.more.profile.favourite.adapters

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.TabsHeaderClick
import com.app.dubaiculture.utils.AppConfigUtils.favouriteClickCheckerFlag
import com.google.android.material.card.MaterialCardView
import com.xwray.groupie.GroupieViewHolder

class FavouriteHeaderItems<T>(
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
            val textView=root.findViewById<TextView>(R.id.tv_title)
            val imageView=root.findViewById<ImageView>(R.id.imgInnerIcon)
            val cardView=root.findViewById<MaterialCardView>(R.id.cardview)
            textView.text = displayValue
            isSelected = favouriteClickCheckerFlag == position
            renderSelection(textView, imageView, cardView)
            root.setOnClickListener {
                progressListener?.onClick(position)
                if (favouriteClickCheckerFlag == position) {
                    isSelected = true
                    renderSelection(textView, imageView, cardView)
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