package com.app.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.local.models.ThreeSixtyImageItem
import com.app.dubaiculture.databinding.FragmentThreeSixtyBinding
import com.app.dubaiculture.databinding.Items360GalleryViewBinding
import com.app.dubaiculture.ui.base.BaseDialogFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery.adapter.ThreeSixtyListItem
import com.app.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery.viewmodel.ThreeSixtyViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.Constants.NavBundles.THREESIXTY_GALLERY_LIST
import com.app.dubaiculture.utils.handleApiError
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ThreeSixtyFragment : BaseDialogFragment<FragmentThreeSixtyBinding>(), View.OnClickListener {
    private val threeSixtyViewModel: ThreeSixtyViewModel by viewModels()
    private lateinit var attractionsObj: Attractions

    @Inject
    lateinit var glide: RequestManager

    override fun getTheme(): Int {
        return R.style.FullScreenDialog;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog?.window!!.apply {
                setLayout(width, height)
                @Suppress("DEPRECATION")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insetsController?.hide(WindowInsets.Type.statusBars())
                } else {
                    setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )
                }

            }

        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            attractionsObj = getParcelable(THREESIXTY_GALLERY_LIST)!!
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(threeSixtyViewModel)
        callingObservables()
        subscribeToObservables()
        initRv()
        binding.imgBack.setOnClickListener(this)
//        initRv()
    }

    private fun callingObservables() {
        attractionsObj.let {
            threeSixtyViewModel.getAttractionDetailsToScreen(
                attractionId = it.id,
                getCurrentLanguage().language
            )
        }
    }

    private fun subscribeToObservables() {
        threeSixtyViewModel.attractionDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
//                    contentFlag = "ContentLoaded"

                    attractionsObj = it.value
                    attractionsObj.asset360?.let { asset ->
                        asset.imageItems?.let { loadVR(it.get(0))
                        binding.three360Title.text = it[0].title
                        } }
                    attractionsObj.asset360?.imageItems?.forEach {
//                        if (groupAdapter.itemCount > 0) {
//                            groupAdapter.clear()
//                        }
                        groupAdapter.add(
                            ThreeSixtyListItem<Items360GalleryViewBinding>(
                                rowClickListener = object : RowClickListener {
                                    override fun rowClickListener(position: Int) {
                                        attractionsObj.asset360?.imageItems?.get(position).let {
                                            binding.three360Title.text = it?.title
                                        }

                                        loadVR(attractionsObj.asset360?.imageItems?.get(position)!!)
                                    }
                                },
                                imageItem = it,
                                glide = glide
                            )
                        )
                    }
//                    initializeDetails(attractionsObj)
                }
                is Result.Failure -> {
                    handleApiError(it, threeSixtyViewModel)
                }
            }
        }
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
            adapter = groupAdapter
        }
    }

    private fun loadVR(image: ThreeSixtyImageItem) {
        binding.mVrPanoramaSelector.apply {
            providePanorama(activity)
            image.image?.let { initialize(it) }
        }
    }

//    companion object {
//        fun drawPoint(canvas: Canvas, point: PointF) {
//            val x = point.x
//            val y = point.y
//
//            canvas.drawCircle(x, y, 6f, Paint().apply {
//                color = Color.CYAN
//                strokeWidth = 3F
//                style = Paint.Style.FILL_AND_STROKE
//                isAntiAlias = true
//
//            })
//
//
//        }
//    }
}
