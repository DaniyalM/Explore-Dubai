package com.app.dubaiculture.ui.postLogin.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.FragmentEventFilterBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionPagerAdaper
import com.app.dubaiculture.ui.postLogin.attractions.components.AttractionHeaderItemSelector
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventFilterFragment : BaseFragment<FragmentEventFilterBinding>() {
    private val eventViewModel: EventViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(eventViewModel)
        callingObservables()
        subscribeToObservables()
        initiatePager()
    }
    private fun initiatePager() {
        binding.pager.isUserInputEnabled = false
    }
    private fun callingObservables() {
        lifecycleScope.launch {
            eventViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
        }
    }

    private fun subscribeToObservables() {
        eventViewModel.attractionCategoryList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.let {
                        binding.horizontalSelector.initialize(it.value, binding.pager)
                        binding.pager.adapter = AttractionPagerAdaper(this, it.value)
                    }
                }
                is Result.Failure -> {
                    binding.horizontalSelector.initialize(createTestItems(), binding.pager)
                    binding.pager.adapter = AttractionPagerAdaper(this,
                        createTestItems() as ArrayList<AttractionCategory>)
//                    handleApiError(it, attractionViewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.horizontalSelector.positionUpdate(AttractionHeaderItemSelector.clickCheckerFlag)
    }


    private fun createTestItems(): List<AttractionCategory> = mutableListOf<AttractionCategory>().apply {


        repeat((1..10).count()) {

            add(
                AttractionCategory(
                    id=it.toString(),
                    title = "Title 1 $it",
                    icon = "",
                    imgSelected = R.drawable.museums_icon_home,
                    imgUnSelected = R.drawable.museum,
                    attractions = createAttractionItems()
                )
            )
        }
    }

    private fun createAttractionItems(): ArrayList<Attractions> = mutableListOf<Attractions>().apply {
        repeat((1..4).count()) {
            add(
                Attractions(
                    id=it.toString(),
                    title = "title $it",
                    category = "Category $it",
                    IsFavourite = false,
                )
            )
        }
    } as ArrayList<Attractions>
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentEventFilterBinding.inflate(inflater,container,false)

}