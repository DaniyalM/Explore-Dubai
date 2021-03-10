package com.app.dubaiculture.ui.postLogin.attractions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.FragmentAttractionsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionPagerAdaper
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionBusService
import com.app.dubaiculture.ui.postLogin.attractions.components.AttractionHeaderItemSelector.Companion.clickCheckerFlag
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AttractionsFragment : BaseFragment<FragmentAttractionsBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()
    private var itemHasLoaded = false

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAttractionsBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        setupToolbarWithSearchItems()
        subscribeUiEvents(attractionViewModel)
        callingObservables()
        subscribeToObservables()
        initiatePager()


    }

    @Subscribe
    fun doRefreshRequest(attractionBusService: AttractionBusService.SwipeToRefresh) {
        if (attractionBusService.doRefresh && !itemHasLoaded) {
            callingObservables()
        }
    }

    private fun initiatePager() {
        binding.pager.isUserInputEnabled = false
    }


    private fun callingObservables() {
        lifecycleScope.launch {
            attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
        }
    }

    private fun subscribeToObservables() {
        attractionViewModel.attractionCategoryList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.let {
                        if (!itemHasLoaded) {
                            itemHasLoaded = true
                            binding.horizontalSelector.initialize(it.value, binding.pager)
                            binding.pager.adapter = AttractionPagerAdaper(this, it.value)

                        }

                    }
                }
                is Result.Failure -> {
                    var items = createTestItems()
                    if (!itemHasLoaded) {

                        itemHasLoaded = true
                        binding.horizontalSelector.initialize(items, binding.pager)
                        binding.pager.adapter =
                            AttractionPagerAdaper(this, items as ArrayList<AttractionCategory>)
                    } else {
                        items = emptyList()
                        items = createTestItems()
                        binding.horizontalSelector.initialize(items, binding.pager)
                        binding.pager.adapter =
                            AttractionPagerAdaper(this, items as ArrayList<AttractionCategory>)
                    }


//                    handleApiError(it, attractionViewModel)
                }
            }
        }
    }


    private fun setupToolbarWithSearchItems() {
//        var searchViewVisibility = false
        binding.root.apply {
            profilePic.visibility = View.GONE
            img_drawer.visibility = View.GONE
            toolbar_title.apply {
                visibility = View.VISIBLE
                text = activity.getString(R.string.attractions)
            }
//            search.setOnClickListener {
//                searchViewVisibility = !searchViewVisibility
//                if (searchViewVisibility) {
//                    binding.root.searchView.visibility = View.VISIBLE
//                    toolbar_title.visibility = View.GONE
//                } else {
//                    binding.root.searchView.visibility = View.GONE
//                    toolbar_title.visibility = View.VISIBLE
//                }
//            }
//
//            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(p0: String?) = true
//                override fun onQueryTextChange(text: String?): Boolean {
//                    text?.let { bus.post(AttractionBusService().SearchTextQuery(it)) }
//                    return true
//                }
//            })

        }
    }

    override fun onResume() {
        super.onResume()
        binding.horizontalSelector.positionUpdate(clickCheckerFlag)
    }


    private fun createTestItems(): List<AttractionCategory> =
        mutableListOf<AttractionCategory>().apply {


            repeat((1..4).count()) {

                add(
                    AttractionCategory(
                        id = it.toString(),
                        title = "Title 1 $it",
                        icon = "",
                        imgSelected = R.drawable.museums_icon_home,
                        imgUnSelected = R.drawable.museum,
                        attractions = createAttractionItems()
                    )
                )
            }
        }

    private fun createAttractionItems(): ArrayList<Attractions> =
        mutableListOf<Attractions>().apply {


            repeat((1..4).count()) {

                add(
                    Attractions(
                        id = it.toString(),
                        title = "title $it",
                        category = "Category $it",
                        IsFavourite = false,
                    )
                )
            }
        } as ArrayList<Attractions>
}

