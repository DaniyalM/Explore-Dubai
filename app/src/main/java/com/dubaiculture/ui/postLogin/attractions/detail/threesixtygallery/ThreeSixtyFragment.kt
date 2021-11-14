package com.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.attraction.local.models.Attractions
import com.dubaiculture.data.repository.attraction.local.models.ThreeSixtyImageItem
import com.dubaiculture.databinding.FragmentThreeSixtyBinding
import com.dubaiculture.databinding.Items360GalleryViewBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery.adapter.ThreeSixtyListItem
import com.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery.viewmodel.ThreeSixtyViewModel
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.utils.Constants.NavBundles.THREESIXTY_GALLERY_LIST
import com.dubaiculture.utils.handleApiError
import com.bumptech.glide.RequestManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ThreeSixtyFragment : BaseFragment<FragmentThreeSixtyBinding>(), View.OnClickListener {
    private val threeSixtyViewModel: ThreeSixtyViewModel by viewModels()
    private lateinit var attractionsObj: Attractions
    var threeSixtyListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()

    @Inject
    lateinit var glide: RequestManager



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

//                    binding.three360Title.visibility = View.GONE
//                    binding.appCompatImageView.visibility = View.GONE
//                    binding.tvNoData.visibility = View.VISIBLE


                    attractionsObj = it.value
                    if (attractionsObj.asset360!!.imageItems.isNullOrEmpty()) {
                        binding.three360Title.visibility = View.GONE
                        binding.appCompatImageView.visibility = View.GONE
                        binding.tvNoData.visibility = View.VISIBLE
                    }

                    attractionsObj.asset360?.let { asset ->
                        asset.imageItems?.let {
                            loadVR(it[0])
                            if (!it[0].title.isNullOrEmpty()) {
                                binding.three360Title.text = it[0].title
                            } else {
                                binding.three360Title.visibility = View.GONE
                            }
                        }
                    }
                    attractionsObj.asset360?.imageItems?.forEach {
//                        if (threeSixtyListAdapter.itemCount > 0) {
//                            threeSixtyListAdapter.clear()
//                        }
                        threeSixtyListAdapter.add(
                            ThreeSixtyListItem<Items360GalleryViewBinding>(
                                rowClickListener = object : RowClickListener {
                                    override fun rowClickListener(position: Int) {
                                        attractionsObj.asset360?.imageItems?.get(position).let {
                                            if (!it?.title.isNullOrEmpty()) {
                                                binding.three360Title.visibility = View.VISIBLE

                                                binding.three360Title.text = it?.title
                                            } else {
                                                binding.three360Title.visibility = View.GONE
                                            }
                                        }

                                        loadVR(attractionsObj.asset360?.imageItems?.get(position)!!)
                                    }

                                    override fun rowClickListener(position: Int, imageView: ImageView) {

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
            adapter = threeSixtyListAdapter
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
