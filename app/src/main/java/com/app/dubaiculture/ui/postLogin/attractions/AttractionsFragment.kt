package com.app.dubaiculture.ui.postLogin.attractions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.FragmentAttractionsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionPagerAdaper
import com.app.dubaiculture.ui.postLogin.attractions.services.AttractionServices
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.utils.AppConfigUtils.clickCheckerFlag
import com.app.dubaiculture.utils.Constants.NavBundles.ATTRACTION_OBJECT
import com.app.dubaiculture.utils.handleApiError
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class AttractionsFragment : BaseFragment<FragmentAttractionsBinding>() {
    private var pagerAdapter: AttractionPagerAdaper? = null
    private val attractionViewModel: AttractionViewModel by viewModels()
    private var contentLoaded = false
    private var attraction: Attractions? = null
    private var backflagNavigation = false

    override fun onResume() {
        super.onResume()
        initiateRequest()
        if (binding.pager.currentItem != clickCheckerFlag) {
            attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            if (it.getParcelable<Attractions>(ATTRACTION_OBJECT) != null) {
                attraction = it.getParcelable(ATTRACTION_OBJECT)
            }

        }
    }


    private fun initiateRequest() {

        binding.swipeRefresh.apply {
            if (!contentLoaded) {
                attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
            }

            setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
            )
            setOnRefreshListener {
                attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)

            }

        }
    }


    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAttractionsBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (backflagNavigation) {
            backflagNavigation = false
            attraction = null
            navigateBack()
        }

        if (attraction != null) {
            backflagNavigation = true
            navigate(R.id.action_attractionsFragment_to_attractionDetailFragment, Bundle().apply {
                putParcelable(
                    ATTRACTION_OBJECT,
                    attraction
                )
            })


        }

        setupToolbarWithSearchItems()
        subscribeUiEvents(attractionViewModel)
        initiatePager()
        subscribeToObservables()

    }


    private fun initiatePager() {
        if (pagerAdapter==null) {
            pagerAdapter = AttractionPagerAdaper(this)
            binding.pager.apply {
                isUserInputEnabled = false
                isSaveEnabled = false
            }
        }




    }


    private fun subscribeToObservables() {
        attractionViewModel.attractionCategoryList.observe(viewLifecycleOwner) {

            if (binding.swipeRefresh.isRefreshing){
                //Below expression will trigger the refresh inside listing fragment
                bus.post(AttractionServices.TriggerRefresh())
                binding.swipeRefresh.isRefreshing = false

            }
            when (it) {
                is Result.Success -> {
                    it.let { result ->
                        contentLoaded = true
                        binding.pager.adapter = pagerAdapter
                        binding.horizontalSelector.initialize(result.value)
                        pagerAdapter?.list = result.value




                    }
                }
                is Result.Failure -> {
                    handleApiError(it, attractionViewModel)
                }
            }
        }
        binding.horizontalSelector.headerPosition.observe(viewLifecycleOwner){
            binding.pager.currentItem = it
        }


    }



    private fun setupToolbarWithSearchItems() {

        binding.root.apply {

            binding.toolbarSnippet.toolbarLayout.profilePic.visibility = View.GONE
            binding.toolbarSnippet.toolbarLayout.imgDrawer.visibility = View.GONE
            binding.toolbarSnippet.toolbarLayout.toolbarTitle.apply {
                visibility = View.VISIBLE
                text = activity.getString(R.string.attractions)
            }
        }
    }


}

