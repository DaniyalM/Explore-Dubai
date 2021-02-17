package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.attraction_title_list_item.view.*

class AttractionHeaderItems<T>(

    val displayValue: String,
    val data: T,
    var isSelected: Boolean = false,
    private val selectedTextColor: Int? = null,
    private val unSelectedTextColor: Int? = null,
    private val selectedBackground: Drawable? = null,
    private val unSelectedBackground: Drawable? = null,

    private val selectedInnerImg: Drawable? = null,
    private val unSelectedInnerImg: Drawable? = null,

    ) : BaseAdapter(R.layout.attraction_title_list_item) {


    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {

            root?.let { it ->
                it.tv_title.text = displayValue
                it.imgInnerIcon.background = selectedInnerImg
                renderSelection(it.tv_title, it.ll_bg, it.imgInnerIcon)

                it.setOnClickListener {
                    isSelected = !isSelected
                    it.imgInnerIcon.background = unSelectedInnerImg
                    renderSelection(it.tv_title, it.ll_bg, it.imgInnerIcon)
                }
            }
        }

    }


    private fun renderSelection(textView: TextView, imageView: ImageView, imgInner: ImageView) {
        if (isSelected) {
            selectedTextColor?.let { color ->
                textView.setTextColor(color)
            }
            selectedBackground?.let { drawable ->
                imageView.background = drawable
            }
            selectedInnerImg?.let { drawable ->
                imgInner.background = drawable
            }
        } else {
            unSelectedTextColor?.let { color ->
                textView.setTextColor(color)
            }
            unSelectedBackground?.let { bg ->
                imageView.background = bg
            }
            unSelectedInnerImg?.let { drawable ->
                imgInner.background = drawable
            }
        }
    }


}