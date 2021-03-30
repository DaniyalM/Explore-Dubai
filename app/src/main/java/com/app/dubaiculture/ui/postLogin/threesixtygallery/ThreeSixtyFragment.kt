package com.app.dubaiculture.ui.postLogin.threesixtygallery

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Gallery
import com.app.dubaiculture.databinding.AttractionGallaryImageItemBinding
import com.app.dubaiculture.databinding.FragmentThreeSixtyBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.gallery.adapter.GalleryListItem
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.threesixtygallery.viewmodel.ThreeSixtyViewModel
import com.app.dubaiculture.utils.Constants
import com.bumptech.glide.RequestManager
import com.google.vr.sdk.widgets.pano.VrPanoramaView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_three_sixty.*
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class ThreeSixtyFragment : BaseFragment<FragmentThreeSixtyBinding>(), View.OnClickListener {
    private val threeSixtyViewModel: ThreeSixtyViewModel by viewModels()
    private var gallerList = ArrayList<Gallery>()


    @Inject
    lateinit var glide: RequestManager
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(threeSixtyViewModel)
        arguments?.apply {
            gallerList = getParcelableArrayList(Constants.NavBundles.ATTRACTION_GALLERY_LIST)!!
        }
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
                gallerList.forEach {
                    add(GalleryListItem<AttractionGallaryImageItemBinding>(
                        rowClickListener = object : RowClickListener {
                            override fun rowClickListener(position: Int) {


                            }
                        },
                        attraction = it,
                        resLayout = R.layout.items_360_gallery_view,
                        glide = glide
                    ))
                }
            }
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun loadVR() {
        val baseFrameLayout = mVrPanoramaView.get(0) as ViewGroup

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

        val options = VrPanoramaView.Options()
        var inputStream: InputStream? = null

//        Load from network
//        val DEMO_PANORAMA_LINK = "http://reznik.lt/wp-content/uploads/2017/09/preview3000.jpg"
//        Glide.with(this).asBitmap().load(DEMO_PANORAMA_LINK).into(object : CustomTarget<Bitmap>() {
//            override fun onLoadCleared(placeholder: Drawable?) { }
//            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                // "option": Declared at the step 3
//                vrPanoramaView.loadImageFromBitmap(resource, option)
//            }
//        })
        val assetManager = requireActivity().assets
        try {
            inputStream = assetManager.open("montains.jpg")
            options.inputType = VrPanoramaView.Options.TYPE_MONO
            mVrPanoramaView!!.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options)
            inputStream.close()
        } catch (e: IOException) {
//            Log.e("Tuts+", "Exception in loadPhotoSphere: " + e.getMessage())
        }
    }
}