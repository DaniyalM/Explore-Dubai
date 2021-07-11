package com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.SiteMap
import com.app.dubaiculture.databinding.FragmentSiteMapBinding
import com.app.dubaiculture.databinding.SiteViewMapItemsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.SITE_MAP_OBJ
import com.bumptech.glide.RequestManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SiteMapFragment : BaseFragment<FragmentSiteMapBinding>(), View.OnClickListener {
    private val siteMapViewModel: SiteMapViewModel by viewModels()
    var siteMapListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()

    @Inject
    lateinit var glide: RequestManager

    lateinit var siteMapObj: SiteMap



    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSiteMapBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            adapter = siteMapListAdapter
        }
    }

    private fun callingObserver() {

        siteMapObj.let {
            glide.load(BuildConfig.BASE_URL + siteMapObj.image)
                .into(binding.siteMap)
            it.siteMap?.forEach {
                siteMapListAdapter.add(
                    SiteMapAdapter<SiteViewMapItemsBinding>(
                        siteMaps = it,
                        resLayout = R.layout.site_view_map_items
                    )
                )
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