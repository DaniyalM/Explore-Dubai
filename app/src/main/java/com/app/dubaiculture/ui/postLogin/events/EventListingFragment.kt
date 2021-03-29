package com.app.dubaiculture.ui.postLogin.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.request.EventRequest
import com.app.dubaiculture.data.repository.filter.models.SelectedItems
import com.app.dubaiculture.databinding.FragmentEventListingBinding
import com.app.dubaiculture.databinding.ItemEventListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.app.dubaiculture.ui.postLogin.events.adapters.FilterHeaderAdapter
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.EVENT_OBJECT
import com.app.dubaiculture.utils.dateFormatEn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventListingFragment : BaseFragment<FragmentEventListingBinding>(), View.OnClickListener {
    private val eventViewModel: EventViewModel by activityViewModels()
    private val allList = mutableListOf<Events>()
    lateinit var adapterEvents: FilterHeaderAdapter
    private var selectedItemsList = ArrayList<SelectedItems>()

    var eventID: Int? = 0
    private var isContentLoaded = false

    companion object {
        var EVENT_CATEG0RY_TYPE: String = "Events"
        var EVENT_DETAIL_ID: String = "Event_ID"

        @JvmStatic
        fun newInstance(eventID: Int? = 0) = EventListingFragment().apply {
            arguments = Bundle().apply {
                putInt(EVENT_DETAIL_ID, eventID!!)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(eventViewModel)
        initRecyclerView()
        callingObservables()
        subscribeToObservables()
        binding.llFilterHeader.setOnClickListener(this)
        callingObservablesForSearchBarKeyWord()
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            eventViewModel.updateHeaderItems(eventID ?: 0)
        }
    }


    private fun callingObservablesForSearchBarKeyWord() {
        eventViewModel.searchBarKeyWord.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                lifecycleScope.launch {
                    eventViewModel.getFilterEventList(EventRequest(
                        culture = getCurrentLanguage().language,
                        keyword = it
                    ))
                }
            }
        }
    }

    private fun callingObservables() {
        lifecycleScope.launch {
            eventViewModel.getDataFilterBtmSheet(locale = getCurrentLanguage().language)
        }
    }

    private fun initRecyclerView() {
        binding.rvEventListing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = groupAdapter
        }
        adapterEvents = FilterHeaderAdapter(object : FilterHeaderAdapter.RemoveHeaderItem {
            override fun onItemRemove(pos: Int, list: List<SelectedItems>) {
                removeItemsFromFilterHeader(pos)
            }
        })
        binding.rvFilterHeader.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterEvents
        }
        callingFilterObserver()
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventListingBinding.inflate(inflater, container, false)


    private fun subscribeToObservables() {
        eventViewModel.eventfilterRequest.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.let {
                        isContentLoaded = false
                        allList.clear()
                        allList.addAll(it.value)

                        groupAdapter.clear()
                        groupAdapter.apply {
//                                    when (it) {
                            if (eventID != 2) {
                                allList.forEach {
                                    add(EventListItem<ItemEventListingBinding>(object :
                                        FavouriteChecker {
                                        override fun checkFavListener(
                                            checkbox: CheckBox,
                                            pos: Int,
                                            isFav: Boolean,
                                            itemId: String,
                                        ) {
                                            favouriteClick(
                                                checkbox,
                                                isFav,
                                                type = 2,
                                                itemId = itemId,
                                                baseViewModel = eventViewModel,
                                                nav = R.id.action_eventFilterFragment_to_postLoginFragment
                                                )
                                        }

                                    },
                                        object : RowClickListener {
                                            override fun rowClickListener(position: Int) {
                                                val eventObj = allList[position]
                                                val bundle = Bundle()
                                                bundle.putParcelable(EVENT_OBJECT, eventObj)
                                                navigate(R.id.action_eventFilterFragment_to_eventDetailFragment2,
                                                    bundle)
                                            }

                                        },
                                        event = it,
                                        resLayout = R.layout.item_event_listing))
                                }
                            } else
                                eventViewModel.getWeekend(allList).forEach {
                                    add(EventListItem<ItemEventListingBinding>(
                                        object : FavouriteChecker {
                                            override fun checkFavListener(
                                                checkbox: CheckBox,
                                                pos: Int,
                                                isFav: Boolean,
                                                itemId: String,
                                            ) {
                                                favouriteClick(
                                                    checkbox,
                                                    isFav,
                                                    type = 2,
                                                    itemId = itemId,
                                                    baseViewModel = eventViewModel,
                                                    nav = R.id.action_eventFilterFragment_to_postLoginFragment
                                                )
                                            }

                                        },
                                        object : RowClickListener {
                                            override fun rowClickListener(position: Int) {
                                                navigate(R.id.action_eventFilterFragment_to_eventDetailFragment2)
                                            }

                                        },
                                        event = it,
                                        resLayout = R.layout.item_event_listing))
                                }
                        }
                        if (allList.isEmpty())
                            binding.tvNoData.visibility = View.VISIBLE
                        else
                            binding.tvNoData.visibility = View.GONE
                    }
                }
                is Result.Failure -> {
                    showErrorDialog(message = Constants.Error.INTERNET_CONNECTION_ERROR)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        arguments?.getInt(EVENT_DETAIL_ID)
            ?.let {
                eventID = it
                eventViewModel.updateHeaderItems(eventID ?: 0)
            }
    }


    private fun callingFilterObserver() {
        eventViewModel.filterDataList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                binding.llFilterHeader.visibility = View.VISIBLE
                selectedItemsList = it
                transformationOfModels(selectedItemsList)
                adapterEvents.selectedItems = it as List<SelectedItems>
            } else {
                binding.llFilterHeader.visibility = View.GONE
                transformationOfModels(selectedItemsList)

            }
        }
    }

    private fun removeItemsFromFilterHeader(position: Int) {
        selectedItemsList.removeAt(position)
        adapterEvents.notifyItemRemoved(position)
        adapterEvents.notifyItemRangeChanged(position, selectedItemsList.size)
        eventViewModel._filterDataList.value = selectedItemsList
        transformationOfModels(selectedItemsList)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_filter_header -> {
                selectedItemsList.clear()
                eventViewModel._filterDataList.value = selectedItemsList
                binding.llFilterHeader.visibility = View.GONE

            }
        }
    }

    private fun transformationOfModels(
        list: ArrayList<SelectedItems>,
    ): ArrayList<EventRequest> {
        val eventRequest = ArrayList<EventRequest>()
        val categoryStringList = ArrayList<String>()
        var keyword: String? = ""
        var location: String? = ""
        var dateFrom: String? = ""
        var dateTo: String? = ""
        var type: String? = ""
        list.forEach {
            if (it.category!!.isNotEmpty()) {
                categoryStringList.add(it.id!!)
            }
            if (it.keyword!!.isNotEmpty()) {
                keyword = it.keyword
            }
            if (it.type!!.isNotEmpty()) {
                type = it.id
            }
            if (it.dateFrom!!.isNotEmpty()) {
                dateFrom = it.dateFrom
            }
            if (it.dateTo!!.isNotEmpty()) {
                dateTo = it.dateTo
            }
            if (it.location!!.isNotEmpty()) {
                location = it.id
            }
        }

        eventRequest.add(
            EventRequest(
                category = categoryStringList,
                keyword = keyword,
                type = type,
                dateFrom = dateFrom,
                dateTo = dateTo,
                location = location
            )
        )

        eventRequest.map {
            lifecycleScope.launch {
                eventViewModel.getFilterEventList(EventRequest(
                    culture = getCurrentLanguage().language,
                    category = it.category,
                    keyword = it.keyword,
                    location = it.location,
                    dateFrom = dateFormatEn(it.dateFrom),
                    dateTo = dateFormatEn(it.dateTo),
                    type = it.type
                ))
            }
        }
        return eventRequest
    }

}