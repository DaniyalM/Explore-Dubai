package com.app.dubaiculture.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.app.dubaiculture.BuildConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rishabhharit.roundedimageview.RoundedImageView


fun View.glideInstance(url: String?): RequestBuilder<Drawable> {
  return Glide.with(this.context).load(BuildConfig.BASE_URL + url)
      .transition(DrawableTransitionOptions.withCrossFade())
}

@BindingAdapter("android:imageUrl")
fun RoundedImageView.loadImage(url: String?) {
    url?.let {
        glideInstance(it).into(this)
    }
}

@BindingAdapter("android:imageViewUrl")
fun ImageView.loadImageView(url: String?) {
    url?.let {
        glideInstance(it).into(this)
    }
}

//@BindingAdapter("android:svgUrl")
//fun ImageView.loadSvgToImageView( url: String?) {
//    url?.let {
//        glideInstance(it).into(this)
//    }
//
//}

