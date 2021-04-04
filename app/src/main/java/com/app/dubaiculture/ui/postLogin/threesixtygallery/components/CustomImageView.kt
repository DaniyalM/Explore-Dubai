package com.app.dubaiculture.ui.postLogin.threesixtygallery.components

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.get
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Assets360
import com.app.dubaiculture.ui.postLogin.threesixtygallery.ThreeSixtyFragment.Companion.drawPoint
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.vr.sdk.widgets.pano.VrPanoramaView

class CustomImageView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var glide: RequestManager? = null

    //   val assetManager = (context as Activity).assets
//
//    val options = Options()
//    var inputStream: InputStream? = null
    private lateinit var panorama: VrPanoramaView

    fun provideGlide(glide: RequestManager) {
        this.glide = glide
    }

    fun providePanorama(context: Activity) {
        val view = inflate(context, R.layout.panorama_view_container, null)
        panorama = view.findViewById(R.id.mVrPanoramaView)

        val baseFrameLayout = panorama[0] as ViewGroup

        // For "Exit fullscreen" button and "About VR View" button
        val relativeLayout = baseFrameLayout.getChildAt(1) as ViewGroup
        val exitFullscreenImageButton = relativeLayout.getChildAt(0)
        val aboutVRViewImageButton = relativeLayout.getChildAt(1)

        // For "Cardboard" button and "Enter fullscreen" button
        val linearLayout = relativeLayout.getChildAt(2) as ViewGroup
        val cardboardImageButton = linearLayout.getChildAt(0)
        val enterFullscreenImageButton = linearLayout.getChildAt(1)


        exitFullscreenImageButton.visibility = View.GONE
        aboutVRViewImageButton.visibility = View.GONE
        cardboardImageButton.visibility = View.GONE
        enterFullscreenImageButton.visibility = View.GONE
        addView(panorama)
    }


    fun initialize(assets360: Assets360) {

        glide?.apply {
            asBitmap().load(assets360.image).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val tempBitmap =
                        Bitmap.createBitmap(resource.width,
                            resource.height,
                           Bitmap.Config.ARGB_8888)

                    val canvas = Canvas(resource)
                    assets360.imageItems?.let {
                        repeat(it.size) {
                            drawPoint(canvas, PointF().apply {
                                x = (resource.width / 2)+it.toFloat()
                                y = (resource.height / 2)+it.toFloat()
                            }, context)
                            invalidate()
                        }
                    }

                    panorama.loadImageFromBitmap(resource, VrPanoramaView.Options())
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })
        }

    }
}