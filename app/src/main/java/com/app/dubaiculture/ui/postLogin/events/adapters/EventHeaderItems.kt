package com.app.dubaiculture.ui.postLogin.events.adapters

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionHeaderClick
import com.app.dubaiculture.ui.postLogin.attractions.components.EventHeaderItemSelector
import com.google.android.material.card.MaterialCardView
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.attraction_title_list_item.view.*

class EventHeaderItems <T>(
    val displayValue: String,
    val data: T,
    var isSelected: Boolean = false,
    private val selectedTextColor: Int? = null,
    private val unSelectedTextColor: Int? = null,
    private val selectedBackground: Drawable? = null,
    private val unSelectedBackground: Drawable? = null,
    private val progressListener: AttractionHeaderClick? = null,

    ) : BaseAdapter(R.layout.attraction_title_list_item) {


    private lateinit var view: View


    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        view=viewHolder.root
        viewHolder.apply {

            root?.let { it ->
                it.ll_bg.visibility = View.GONE
                it.imgInnerIcon.visibility = View.GONE
                it.tv_title.text = displayValue
                isSelected = EventHeaderItemSelector.clickCheckerFlag ==position
                renderSelection(it.tv_title, it.ll_bg, it.imgInnerIcon, it.cardview)
                it.setOnClickListener {
                    progressListener?.onClick(position)

                    if (EventHeaderItemSelector.clickCheckerFlag ==position){
                        isSelected=true
                        renderSelection(it.tv_title, it.ll_bg, it.imgInnerIcon, it.cardview)
                    }
                }
            }
        }
    }


    private fun renderSelection(
        textView: TextView, imageView: ImageView, imgInner: ImageView,
        view: MaterialCardView,
    ) {
        if (isSelected) {
            view.setCardBackgroundColor(ContextCompat.getColor(view.context,
                R.color.purple_900))
            selectedTextColor?.let { color ->

                textView.setTextColor(color)
            }
            selectedBackground?.let { drawable ->
                imageView.background = drawable
            }
//            selectedInnerImg?.let { drawable ->
//                imgInner.background = drawable
//                imgInner.setColorFilter(ContextCompat.getColor(view.context, R.color.white_900))
//            }

        } else {
            view.setCardBackgroundColor(ContextCompat.getColor(view.context,
                R.color.white_900))
            unSelectedTextColor?.let { color ->


                textView.setTextColor(color)
            }
            unSelectedBackground?.let { bg ->
                imageView.background = bg
            }
//            unSelectedInnerImg?.let { drawable ->
//                imgInner.background = drawable
//                imgInner.setColorFilter(ContextCompat.getColor(view.context, R.color.purple_900))
//            }
        }
    }
}