package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionHeaderClick
import com.app.dubaiculture.utils.AppConfigUtils.clickCheckerFlag
//import com.app.dubaiculture.ui.postLogin.attractions.components.AttractionHeaderItemSelector.Companion.clickCheckerFlag
import com.app.dubaiculture.utils.glideInstance
import com.google.android.material.card.MaterialCardView
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.attraction_title_list_item.view.*


class AttractionHeaderItems<T>(
        val displayValue: String,
    val data: T,
    var isSelected: Boolean = false,
    private val selectedInnerImg: String? = null,
    private val unSelectedInnerImg: String? = null,
    private val progressListener: AttractionHeaderClick? = null,
    private val colorBg  : String? =null
) : BaseAdapter(R.layout.attraction_title_list_item) {


    private lateinit var view: View


    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        view = viewHolder.root
        viewHolder.apply {

            root?.let { it ->
                it.tv_title.text = displayValue
                isSelected = clickCheckerFlag == position
                renderSelection(it.tv_title, it.imgInnerIcon,  it.cardview)
                it.setOnClickListener {
                    progressListener?.onClick(position)
                    if (clickCheckerFlag == position) {
                        isSelected = true
                        renderSelection(it.tv_title, it.imgInnerIcon,  it.cardview)
                    }

                }


            }
        }

    }


    private fun renderSelection(
        textView: TextView,  imgInner: ImageView,
        view: MaterialCardView,
    ) {
        if (isSelected) {
            view.setCardBackgroundColor(Color.parseColor(colorBg))
            textView.setTextColor(ContextCompat.getColor(view.context,R.color.white_900))
            selectedInnerImg?.let { drawable ->
                imgInner.glideInstance(drawable, true).into(imgInner)
                imgInner.setColorFilter(ContextCompat.getColor(view.context, R.color.white_900))
            }

        } else {
            view.setCardBackgroundColor(ContextCompat.getColor(view.context,
                R.color.white_900))
            textView.setTextColor(ContextCompat.getColor(view.context,R.color.purple_900))
            unSelectedInnerImg?.let { drawable ->
                imgInner.glideInstance(drawable, true).into(imgInner)
                imgInner.setColorFilter(ContextCompat.getColor(view.context, R.color.purple_900))
            }
        }

    }


}



