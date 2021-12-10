package com.dubaiculture.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.dubaiculture.BuildConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dubaiculture.R
import com.google.android.material.card.MaterialCardView
import com.rishabhharit.roundedimageview.RoundedImageView
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("android:animate")
fun View.animate(animation: Techniques) {
    YoYo.with(animation)
        .delay(100)
        .playOn(this)
}

fun View.glideInstance(
    url: String?,
    isSvg: Boolean = false,
    showPlaceHolder: Boolean = false
): RequestBuilder<Drawable> {
    var urlConcat = BuildConfig.BASE_URL + url
//    val urlConcat="http://dc.wewanttraffic.me/api/" + url

    val glide = Glide.with(this.context)
    return if (!isSvg) {
        if (urlConcat.contains("Uploads")) {
            urlConcat = urlConcat.replace("/api/~", "")
        }
        val options =
            RequestOptions()
//                .placeholder(R.drawable.logo)
//                .error(android.R.drawable.stat_notify_error)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        if (showPlaceHolder)
            options.placeholder(R.drawable.ic_launcher)
        glide.setDefaultRequestOptions(
            options
        ).load(urlConcat)
//                .transition(DrawableTransitionOptions.withCrossFade())


    } else {
        glide
            .load(urlConcat)
//                .transition(DrawableTransitionOptions.withCrossFade())
    }
}

@BindingAdapter("android:imageUrl")
fun RoundedImageView.loadImage(url: String?) {
    url?.let {
        glideInstance(it).into(this)
    }
}

@BindingAdapter("android:imageOrPlaceHolder")
fun RoundedImageView.loadImageWithPlaceHolder(url: String?) {
    if (url.isNullOrEmpty()) {
        glideInstance(url, showPlaceHolder = true).into(this)
    } else {
        glideInstance(url).into(this)
    }
}

@BindingAdapter("android:loadImage")
fun CircleImageView.loadImage(url: String?) {
    url?.let {
        glideInstance(it).into(this)
    }
}

@BindingAdapter("android:imageViewUrl")
fun ImageView.loadImageView(url: String?) {
    url?.let {
        if (it.contains(".svg")) {
            glideInstance(it, true).into(this)
        } else {
            glideInstance(it).into(this)
        }

    }
}


@BindingAdapter("android:cardParsedColor")
fun MaterialCardView.backColor(color: String?) {
    color?.let {
        this.setCardBackgroundColor(Color.parseColor(color))
    }
}


