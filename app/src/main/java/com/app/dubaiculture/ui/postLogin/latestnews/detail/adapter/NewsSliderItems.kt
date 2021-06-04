package com.app.dubaiculture.ui.postLogin.latestnews.detail.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.databinding.ItemSliderBinding
import com.app.dubaiculture.utils.glideInstance
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.xwray.groupie.databinding.BindableItem

data class NewsSliderItems <T : ViewDataBinding>(
        val latestNews: LatestNews,
        val resLayout: Int = R.layout.item_slider,
        val context: Context,
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemSliderBinding ->{
                viewBinding.let {
                    YoYo.with(Techniques.BounceInUp)
                        .duration(1000)
                        .playOn(it.root)
                    it.imgSlider.glideInstance(latestNews.image, false).into(it.imgSlider)
                }
            }
        }
    }

    override fun getLayout() = resLayout


}