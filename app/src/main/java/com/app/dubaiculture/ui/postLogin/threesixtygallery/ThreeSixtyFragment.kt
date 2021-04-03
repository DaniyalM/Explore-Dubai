package com.app.dubaiculture.ui.postLogin.threesixtygallery

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Assets360
import com.app.dubaiculture.data.repository.attraction.local.models.Gallery
import com.app.dubaiculture.databinding.FragmentThreeSixtyBinding
import com.app.dubaiculture.databinding.Items360GalleryViewBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.threesixtygallery.adapter.ThreeSixtyListItem
import com.app.dubaiculture.ui.postLogin.threesixtygallery.viewmodel.ThreeSixtyViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.THREESIXTY_GALLERY_LIST
import com.bumptech.glide.RequestManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_three_sixty.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ThreeSixtyFragment : BaseFragment<FragmentThreeSixtyBinding>(), View.OnClickListener {
    private val threeSixtyViewModel: ThreeSixtyViewModel by viewModels()
    private var gallerList = ArrayList<Gallery>()

    private lateinit var assets360: Assets360


    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            assets360 = getParcelable(THREESIXTY_GALLERY_LIST)!!
        }
    }
    @Inject
    lateinit var glide: RequestManager
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(threeSixtyViewModel)

        binding.imgBack.setOnClickListener(this)
        initRv()
        loadVR()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentThreeSixtyBinding.inflate(inflater, container, false)

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_back -> {
                back()
            }
        }
    }

    private fun initRv() {
        binding.rvBottomSelector.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = GroupAdapter<GroupieViewHolder>().apply {
                assets360.imageItems?.forEach {
                    add(ThreeSixtyListItem<Items360GalleryViewBinding>(
                        rowClickListener = object : RowClickListener {
                            override fun rowClickListener(position: Int) {
                                Timber.e("Click Handling of Points")
                            }
                        },
                        imageItem = it))
                }
            }
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun loadVR() {
        binding.mVrPanoramaSelector.apply {
            providePanorama(activity)
            provideGlide(glide)
            initialize(assets360)
        }


    }
    companion object {
        fun drawPoint(canvas: Canvas, point: PointF,context: Context?=null) {
            val x = point.x
            val y = point.y

            val paint= Paint()
            val scale: Float = context!!.resources.displayMetrics.density

            paint.apply {
                color=Color.BLUE
                textSize=24*scale

                canvas.drawText("Hello", x, y,
                    this);
            }

            canvas.drawCircle(x, y, 10f, Paint().apply {
                color = Color.RED
                strokeWidth = 8F
                style = Paint.Style.STROKE

            })



        }
    }
}

//private fun drawOnFace(faceArray: SparseArray<Face>): Bitmap? {
//    tempBitmap =
//        Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565)
//    val canvas = Canvas(tempBitmap)
//    canvas.drawBitmap(myBitmap, 0, 0, null)
//    for (i in 0 until faceArray.size()) {
//        val face: Face = faceArray[i]
//        for (landmark in face.getLandmarks()) {
//            when (landmark.getType()) {
//                Landmark.LEFT_EYE -> drawPoint(canvas, landmark.getPosition())
//                Landmark.RIGHT_EYE -> drawPoint(canvas, landmark.getPosition())
//            }
//        }
//    }
//    return tempBitmap
//}

