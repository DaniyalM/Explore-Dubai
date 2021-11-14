package com.dubaiculture.ui.postLogin.more.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.dubaiculture.R
import com.dubaiculture.data.repository.more.MoreModel
import com.dubaiculture.databinding.ItemsMoreLayoutBinding
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.ui.postLogin.more.enum.MoreNewsType
import com.xwray.groupie.databinding.BindableItem

data class MoreItems<T : ViewDataBinding>(
        private val rowClickListener: RowClickListener? = null,
        val moreModel: MoreModel,
        val resLayout: Int = R.layout.items_more_layout,
        val context: Context,
        val isArabic: Boolean,
        val isVisible: Boolean = false
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemsMoreLayoutBinding -> {
                viewBinding.let {
                    it.img.setImageResource(moreModel.leftImg!!)
                    it.tvTitle.text = moreModel.title
                    if (!moreModel.dividerLine) {
                        it.dividerLine.visibility = View.VISIBLE
                    } else {
                        it.dividerLine.visibility = View.INVISIBLE
                    }
//                    if (moreModel.title==context.resources.getString(R.string.settings)){
//                        if (isVisible){
//                            it.rootView.visibility=View.GONE
//                        }
//                    }
                    if (moreModel.title == context.resources.getString(R.string.logout_more)) {
                        if (isVisible){
                              it.rootView.visibility=View.GONE
                        }
                        it.arrow.visibility = View.GONE
                    }
                    it.rootView.setOnClickListener {
                        rowClickListener?.rowClickListener(position)
                        MoreNewsType.fromId(position)

                    }

                }
            }
        }
    }

    override fun getLayout() = resLayout


}