package com.dubaiculture.ui.postLogin.attractions.clicklisteners

import android.widget.ImageView
import com.dubaiculture.data.repository.attraction.local.models.Attractions

interface AttractionClickListener {
    fun rowClickListener(attraction:Attractions)
    fun rowClickListener(position: Int,imageView: ImageView,attraction:Attractions)
}