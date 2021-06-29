package com.app.dubaiculture.ui.postLogin.attractions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.databinding.FragmentAttractionsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionPagerAdaper
import com.app.dubaiculture.ui.postLogin.attractions.services.AttractionServices
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.utils.AppConfigUtils.clickCheckerFlag
import com.app.dubaiculture.utils.handleApiError
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_layout.view.*


@AndroidEntryPoint
class AttractionsFragment : BaseFragment<FragmentAttractionsBinding>() {
    private lateinit var pagerAdapter: AttractionPagerAdaper
    private val attractionViewModel: AttractionViewModel by viewModels()
    private var contentLoaded = false

    override fun onResume() {
        super.onResume()
        if (binding.pager.currentItem!= clickCheckerFlag){
            attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
        }


    }


    private fun initiateRequest() {

        binding.swipeRefresh.apply {
            setColorSchemeResources(R.color.colorPrimary,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_blue_dark)
            setOnRefreshListener {
                contentLoaded = false
                attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
            }
            if (!contentLoaded) {
                post {
                    binding.swipeRefresh.isRefreshing = true
                    attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
                }
            }

        }
    }


    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentAttractionsBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarWithSearchItems()
        subscribeUiEvents(attractionViewModel)
        initiatePager()
        subscribeToObservables()
        initiateRequest()

    }


    private fun initiatePager() {
        pagerAdapter = AttractionPagerAdaper(this)
        binding.pager.apply {
            isUserInputEnabled = false
            isSaveEnabled = false
            adapter = pagerAdapter
        }


    }


    private fun subscribeToObservables() {
        attractionViewModel.attractionCategoryList.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false

            when (it) {
                is Result.Success -> {
                    it.let { result ->
                        contentLoaded = true

                        binding.horizontalSelector.initialize(result.value, bus)
                        pagerAdapter.list = result.value

                        //Below expression will trigger the refresh inside listing fragment
                        bus.post(AttractionServices.TriggerRefresh())


                    }
                }
                is Result.Failure -> {
                    handleApiError(it, attractionViewModel)
                }
            }
        }


    }

    @Subscribe
    fun handleCategoryClick(attractionServices: AttractionServices) {
        when (attractionServices) {
            is AttractionServices.CategoryClick -> {
                binding.pager.currentItem = attractionServices.position
            }
        }

    }


    private fun setupToolbarWithSearchItems() {
        binding.root.apply {
            profilePic.visibility = View.GONE
            img_drawer.visibility = View.GONE
            toolbar_title.apply {
                visibility = View.VISIBLE
                text = activity.getString(R.string.attractions)
            }
        }
    }


}

