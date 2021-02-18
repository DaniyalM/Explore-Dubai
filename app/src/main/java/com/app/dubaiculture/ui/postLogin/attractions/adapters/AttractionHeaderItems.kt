package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.Attraction
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.xwray.groupie.GroupAdapter
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
    private val attractionPager: ViewPager2? = null,
    private val groupAdapter: GroupAdapter<GroupieViewHolder>? = null,

    ) : BaseAdapter(R.layout.attraction_title_list_item) {


    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        val data = data as List<Attraction>
        viewHolder.apply {
            root?.let { it ->
//                it.tv_title.text = displayValue
//                it.imgInnerIcon.background = selectedInnerImg
//                renderSelection(it.tv_title, it.ll_bg, it.imgInnerIcon, it.selectorViewChanger)

                it.setOnClickListener {

                    attractionPager?.currentItem = position
                    for (i in 0 until data.size){
                        data.get(i).isSelected=(i==position)
                        isSelected = i==position
                        it.imgInnerIcon.background = unSelectedInnerImg
                        renderSelection(it.tv_title, it.ll_bg, it.imgInnerIcon, it.selectorViewChanger)
                    }
                    groupAdapter?.notifyDataSetChanged()


//                    isSelected = !isSelected

                }
            }
        }

    }


    private fun renderSelection(
        textView: TextView, imageView: ImageView, imgInner: ImageView,
        view: View,
    ) {


        if (isSelected) {
            selectedTextColor?.let { color ->
                view.selectorViewChanger.setBackgroundColor(ContextCompat.getColor(view.context,
                    R.color.purple_900))
                textView.setTextColor(color)
            }
            selectedBackground?.let { drawable ->
                imageView.background = drawable
            }
            selectedInnerImg?.let { drawable ->
                imgInner.background = drawable
            }

        }else{
             unSelectedTextColor?.let { color ->
                view.selectorViewChanger.setBackgroundColor(ContextCompat.getColor(view.context,
                    R.color.white_900))

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



