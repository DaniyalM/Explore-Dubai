package com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.SiteMap
import com.app.dubaiculture.databinding.FragmentSiteMapBinding
import com.app.dubaiculture.databinding.SiteViewMapItemsBinding
import com.app.dubaiculture.ui.base.BaseDialogFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.SITE_MAP_OBJ
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SiteMapFragment : BaseDialogFragment<FragmentSiteMapBinding>(), View.OnClickListener {
    private val siteMapViewModel: SiteMapViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    lateinit var siteMapObj: SiteMap

    override fun getTheme() = R.style.FullScreenDialog;

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

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSiteMapBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(siteMapViewModel)
        arguments.let {
            siteMapObj = it!!.getParcelable(SITE_MAP_OBJ)!!
//            siteMapViewModel.siteMap(it?.getString(SITE_MAP).toString(),getCurrentLanguage().language)
        }
        callingObserver()
        backArrowRTL(binding.imgClose)
        binding.imgClose.setOnClickListener(this)

    }

    private fun rvSetUp() {
        binding.rvSiteMap.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
        }
    }

    private fun callingObserver() {

        siteMapObj.let {
            glide.load(BuildConfig.BASE_URL + siteMapObj.image)
                .into(binding.siteMap)
            it.siteMap?.forEach {
                groupAdapter.add(SiteMapAdapter<SiteViewMapItemsBinding>(
                    siteMaps = it,
                    resLayout = R.layout.site_view_map_items
                ))
            }
            rvSetUp()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_close -> {
                back()
            }
        }
    }
}