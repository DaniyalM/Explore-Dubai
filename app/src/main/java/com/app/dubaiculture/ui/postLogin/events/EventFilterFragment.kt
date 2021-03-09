package com.app.dubaiculture.ui.postLogin.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.request.HomeEventListRequest
import com.app.dubaiculture.databinding.FragmentEventFilterBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.components.EventHeaderItemSelector
import com.app.dubaiculture.ui.postLogin.events.adapters.EventPagerAdapter
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
            eventViewModel.getEventHomeToScreen(getCurrentLanguage().language)
        }
    }

    private fun subscribeToObservables() {
        eventViewModel.eventCategoryList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.let {
                        binding.horizontalSelector.initialize(it.value, binding.pager)
                        binding.pager.adapter = EventPagerAdapter(this, it.value)
                    }
                }
                is Result.Failure -> {
                    binding.horizontalSelector.initialize(createTestItems(), binding.pager)
                    binding.pager.adapter = EventPagerAdapter(this,
                        createTestItems() as ArrayList<EventHomeListing>)
//                    handleApiError(it, attractionViewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.horizontalSelector.positionUpdate(EventHeaderItemSelector.clickCheckerFlag)
    }

    private fun createTestItems(): List<EventHomeListing> = mutableListOf<EventHomeListing>().apply {
        repeat((1..10).count()) {
            add(
                EventHomeListing(
                    category = it.toString(),
                    events = createAttractionItems()
                )
            )
        }
    }

    private fun createAttractionItems(): ArrayList<Events> = mutableListOf<Events>().apply {
        repeat((1..4).count()) {
            add(Events(
                id = it.toString(),
                title = "title $it",
                category = "Category $it",
                image = "image $it",
                fromDate = "fromDate $it",
                fromMonthYear = "fromMonthYear $it",
                fromTime = "fromTime $it",
                fromDay = "fromDay $it",
                toDate = "toDate $it",
                toMonthYear ="toMonthYear $it",
                toTime = "toTime $it",
                toDay = "toDay $it",
                type ="type $it"
                )
            )
        }
    } as ArrayList<Events>
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentEventFilterBinding.inflate(inflater,container,false)

}