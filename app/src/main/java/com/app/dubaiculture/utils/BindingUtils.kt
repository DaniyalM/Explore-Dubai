package com.app.dubaiculture.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.rishabhharit.roundedimageview.RoundedImageView


fun View.glideInstance(url: String?,isSvg:Boolean=false): RequestBuilder<Drawable> {
    val urlConcat=BuildConfig.BASE_URL + url
//    val urlConcat="http://dc.wewanttraffic.me/api/" + url
    val glide=Glide.with(this.context)
    return  if (!isSvg){
        glide.setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.logo)
                .error(android.R.drawable.stat_notify_error)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
        ).load(urlConcat).transition(DrawableTransitionOptions.withCrossFade())
    }else{
        glide
            .load(urlConcat)
            .transition(DrawableTransitionOptions.withCrossFade())
    }
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
        if (it.contains(".svg")){
            glideInstance(it,true).into(this)
        }else{
            glideInstance(it).into(this)
        }

    }
}


