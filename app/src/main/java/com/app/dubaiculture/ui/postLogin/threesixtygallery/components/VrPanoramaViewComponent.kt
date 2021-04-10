package com.app.dubaiculture.ui.postLogin.threesixtygallery.components

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.core.view.get
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Assets360
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.vr.sdk.widgets.pano.VrPanoramaView


class VrPanoramaViewComponent(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var glide: RequestManager? = null
    lateinit var view: View
    lateinit var relativeLayout: RelativeLayout

    //   val assetManager = (context as Activity).assets
//
//    val options = Options()
//    var inputStream: InputStream? = null
    private lateinit var panorama: VrPanoramaView

    fun provideGlide(glide: RequestManager) {
        this.glide = glide
    }

    fun providePanorama(context: Activity) {
        view = inflate(context, R.layout.panorama_view_container, null)
        relativeLayout = view.findViewById(R.id.containerView)
        panorama = relativeLayout.findViewById(R.id.mVrPanoramaView)

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

        addView(view)
    }


    fun initialize(assets360: Assets360) {

        glide!!.apply {
            asBitmap().load(assets360.image).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    panorama.loadImageFromBitmap(resource, VrPanoramaView.Options())

//                    val canvas = Canvas(resource)
//                    assets360.imageItems?.let { items ->
//                        val paint = Paint(Paint.FILTER_BITMAP_FLAG)
//                        val scale: Float = context!!.resources.displayMetrics.density
//
//                        paint.apply {
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                                color = context.getColor(R.color.white_900)
//                            }
//                            textSize = 6 * scale
//                            style = Paint.Style.FILL_AND_STROKE
//                            isAntiAlias = true
//
//                        }
//                        repeat(items.size) {
//
//
//                            drawPoint(canvas, PointF().apply {
//                                if (it == 0) {
//                                    x = (resource.width / 2) + it.toFloat()
////                                x = items[it].xAxis!!.toFloat()
//                                    y = (resource.height / 2) + it.toFloat()
////                                y = items[it].yAxis!!.toFloat()
//                                    canvas.drawText(items[it].title!!, x + 15, y - 15, paint)
////                                    canvas.drawBitmap(bitmap, x, y, paint)
//
//                                }
//
//                            })
//
//                            val imageView = ImageView(context).apply {
//                                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                                    ViewGroup.LayoutParams.WRAP_CONTENT)
//
//
//                                id = 220 + it
//                                setImageDrawable(ContextCompat.getDrawable(context,
//                                    R.drawable.ellipse))
//                                background =
//                                    ContextCompat.getDrawable(context, R.color.colorPrimaryDark)
//
//                                setOnClickListener {
//
//                                }
//                            }
//
//                            panorama.addView(imageView)
//                            invalidate()
//                        }
//                    }
//                    Toast.makeText(context,"Scrolling Working",Toast.LENGTH_SHORT).show()
//                    panorama.draw(canvas.apply { drawBitmap() })
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })
        }

    }
}