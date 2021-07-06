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
import kotlinx.android.synthetic.main.toolbar_layout.view.*


@AndroidEntryPoint
class AttractionsFragment : BaseFragment<FragmentAttractionsBinding>() {
    private lateinit var pagerAdapter: AttractionPagerAdaper
    private val attractionViewModel: AttractionViewModel by viewModels()
    private var contentLoaded = false
    private var attraction: Attractions? = null
    private var backflagNavigation = false

    override fun onResume() {
        super.onResume()
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
                post {
                    binding.swipeRefresh.isRefreshing = true
                    attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
                }
            }

            setColorSchemeResources(
                    R.color.colorPrimary,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_blue_dark
            )
            setOnRefreshListener {
                attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
                bus.post(AttractionServices.TriggerRefresh())
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
        initiateRequest()
    }


    private fun initiatePager() {
        if (!contentLoaded) {
            pagerAdapter = AttractionPagerAdaper(this)
            binding.pager.apply {
                isUserInputEnabled = false
                isSaveEnabled = true
                adapter = pagerAdapter
            }

        }
    }


    private fun subscribeToObservables() {
        attractionViewModel.attractionCategoryList.observe(viewLifecycleOwner) {
            //Below expression will trigger the refresh inside listing fragment
            binding.swipeRefresh.isRefreshing = false


            when (it) {
                is Result.Success -> {
                    it.let { result ->
                        contentLoaded = true

                        binding.horizontalSelector.initialize(result.value, bus)
                        pagerAdapter.list = result.value


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

