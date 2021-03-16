package com.app.dubaiculture.ui.postLogin.events

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentEventFilterBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.components.EventHeaderItemSelector
import com.app.dubaiculture.ui.postLogin.events.adapters.EventPagerAdapter
import com.app.dubaiculture.ui.postLogin.events.filter.FilterFragment
import com.app.dubaiculture.ui.postLogin.events.filter.viewmodel.FilterViewModel
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.event_search_toolbar.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventFilterFragment : BaseFragment<FragmentEventFilterBinding>(), View.OnClickListener {
    private val eventViewModel: EventViewModel by viewModels()
    private val filterViewModel: FilterViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.root.img_filter.setOnClickListener(this)
        binding.root.back.setOnClickListener(this)
        subscribeUiEvents(eventViewModel)
        callingObservables()
        subscribeToObservables()
        initiatePager()
        if(filterViewModel.filterData.value != null){
            Log.e("Model here=>", filterViewModel.filterData.value!!.size.toString())
        }
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

//                        binding.horizontalSelector.initialize(it.value, binding.pager)
//                        binding.pager.adapter = EventPagerAdapter(this, it.value)
                        binding.horizontalSelector.initialize(createTestItems(), binding.pager)
                        binding.pager.adapter = EventPagerAdapter(this, "1")
                    }
                }
                is Result.Failure -> {

//                    handleApiError(it, attractionViewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.horizontalSelector.positionUpdate(EventHeaderItemSelector.clickCheckerFlag)
    }

    private fun createTestItems(): List<EventHomeListing> =
        mutableListOf<EventHomeListing>().apply {
            repeat((1..5).count()) {
                when(it){
                    0->{
                        add(
                            EventHomeListing(
                                category = "All",
                                events = createAttractionItems()
                            ))
                    }
                    1->{
                        add(
                            EventHomeListing(
                                category = "This Week",
                                events = createAttractionItems()
                            ))
                    }
                    2->{
                        add(
                            EventHomeListing(
                                category = "This Weekend",
                                events = createAttractionItems()
                            ))
                    }
                    3->{
                        add(
                            EventHomeListing(
                                category = "Next 7 Days",
                                events = createAttractionItems()
                            ))
                    }
                }

            }
        }

    private fun createAttractionItems(): ArrayList<Events> = mutableListOf<Events>().apply {
        repeat((1..4).count()) {
            add(Events(
                id = it.toString(),
                title = "Photographs in Dialogue\nexhibition: Hala Badri welcomes",
                category = "ONLINE",
                image = "https://upload.wikimedia.org/wikipedia/commons/c/cc/Dubai_Skylines_at_night_%28Pexels_3787839%29.jpg",
                fromDate = "14",
                fromMonthYear = "NOV, 20",
                fromTime = "fromTime $it",
                fromDay = "fromDay $it",
                toDate = "20",
                toMonthYear = "NOV, 20",
                toTime = "toTime $it",
                toDay = "toDay $it",
                type = "FREE $it"
            )
            )
        }
    } as ArrayList<Events>

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventFilterBinding.inflate(inflater, container, false)

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_filter->{
                navigate(R.id.action_eventFilterFragment_to_filterFragment)
            }
            R.id.back->{
                back()
            }
        }
    }
//    override fun onItemClick(item: String?) {
//        filterViewModel.showToast(item!!)
//    }
}