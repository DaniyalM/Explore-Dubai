package com.app.dubaiculture.ui.postLogin.events

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.request.EventRequest
import com.app.dubaiculture.databinding.FragmentEventListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListScreenAdapter
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.dateFormat
import com.app.dubaiculture.utils.getDateObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class EventListingFragment : BaseFragment<FragmentEventListingBinding>() {
    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var eventListScreenAdapter: EventListScreenAdapter
    private val allList = mutableListOf<Events>()
    private var thisWeeklist = mutableListOf<Events>()
    private var thisWeekendList = mutableListOf<Events>()
    private var next7DaysList = mutableListOf<Events>()

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


        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            // bus.post(AttractionBusService().SwipeToRefresh(true))
        }
    }
    private fun callingObservables() {
//        if (!isContentLoaded) {
            lifecycleScope.launch {
                eventViewModel.getFilterEventList(EventRequest(
                    culture = getCurrentLanguage().language
                ))
//            }
        }

    }

    private fun initRecyclerView() {
//        eventListScreenAdapter = EventListScreenAdapter(object : FavouriteChecker {
//            override fun checkFavListener(checkbox: CheckBox, pos: Int, isFav: Boolean) {
//                favouriteEvent(application.auth.isGuest,
//                    checkbox,
//                    isFav,
//                    R.id.action_eventFilterFragment_to_postLoginFragment)
//            }
//        }, object : RowClickListener {
//            override fun rowClickListener() {
//                navigate(R.id.action_eventFilterFragment_to_eventDetailFragment2)
//            }
//        })
        binding.rvEventListing.apply {
            layoutManager = LinearLayoutManager(activity)
//            adapter = eventListScreenAdapter
            adapter=groupAdapter
//            eventListScreenAdapter.events = createAttractionItems()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        arguments?.getInt(EVENT_DETAIL_ID)
//            ?.let {
//                eventID = it
//                Timber.e("Get Position =>${eventID.toString()}")
//            }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventListingBinding.inflate(inflater, container, false)

    private fun createAttractionItems(): ArrayList<Events> = mutableListOf<Events>().apply {
        repeat((1..4).count()) {
            add(
                Events(
                    id = it.toString(),
                    title = "The Definitive Guide to an Uncertain World",
                    category = "ONLINE",
                    fromDate = "18",
                    fromMonthYear = "Mar, 21",
                    fromTime = "20$it",
                    fromDay = "1$it",
                    toDate = "20",
                    toMonthYear = "Mar, 21",
                    toTime = "202$it",
                    toDay = "2$it",
                    type = "FREE",
                    locationTitle = "Palm Jumeriah, Dubai"
                )
            )
        }
    } as ArrayList<Events>

    private fun subscribeToObservables() {
        eventViewModel.eventfilterRequest.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.let {
                        isContentLoaded = false
                        allList.addAll(it.value)
                        thisWeeklist = thisWeek(it.value)
                        thisWeekendList = getWeekend(it.value)
                        next7DaysList = next7days(it.value)

                        arguments?.getInt(EVENT_DETAIL_ID)
                            ?.let {
                                eventID = it
                                Timber.e("Get Position =>${eventID.toString()}")

                                groupAdapter.apply {
                                    when(it){
                                        0->{
                                            allList.forEach {
                                                add(EventListItem(event = it))
                                            }
//                                            eventListScreenAdapter.events = allList
                                        }
                                        1->{
                                            thisWeeklist.forEach {
                                                add(EventListItem(event = it))
                                            }
//                                            eventListScreenAdapter.events = thisWeeklist

                                        }
                                        2->{
                                            thisWeekendList.forEach {
                                                add(EventListItem(event = it))
                                            }
//                                            eventListScreenAdapter.events = thisWeekendList

                                        }
                                        3->{
                                            next7DaysList.forEach {
                                                add(EventListItem(event = it))
                                            }
//                                            eventListScreenAdapter.events = next7DaysList

                                        }
                                    }
                                }

                            }
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

    }


    private fun thisWeek(list: List<Events>): MutableList<Events> {
        val thisWeekList = mutableListOf<Events>()
        list.forEach { eventDate ->
            for (element in getWeek()) {
                if (element == (dateFormat(eventDate.dateTo))) {
                    thisWeekList.add(eventDate)
                    Timber.e("week list size  ${thisWeekList.size.toString()}")
                    break
                }
            }

        }
        return thisWeekList
    }

    private fun next7days(list: List<Events>): MutableList<Events> {
        val sevenDays = mutableListOf<Events>()
        list.forEach { eventDate ->
            for (element in getNextSevenDays()) {
                if (element == (dateFormat(eventDate.dateTo))) {
                    sevenDays.add(eventDate)
                    Timber.e("week list size  ${sevenDays.size.toString()}")
                    break
                }
            }
        }
        return sevenDays as MutableList<Events>
    }

    private fun getWeekend(list: List<Events>): MutableList<Events> {
        val weekendList = list.filter {
            it.fromDay== "Friday" || it.fromDay == "Saturday"
        }
        return weekendList as MutableList<Events>
    }

    private fun getNextSevenDays(dateString: String? = null): Array<String?> {
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar: Calendar = GregorianCalendar()
        val date = getDateObj("2021-03-28T17:19:00")
        calendar.time = date
        val next7Days = arrayOfNulls<String>(7)
        for (i in 0..6) {
            calendar.add(Calendar.DATE, i)
            next7Days[i] = format.format(calendar.time)
        }
        return next7Days
    }

    private fun getWeek(dateString: String? = null): Array<String?> {
        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar[Calendar.DAY_OF_WEEK] = Calendar.MONDAY
        val date = getDateObj("2021-03-28T17:19:00")
        calendar.time = date
        val days = arrayOfNulls<String>(7)
        for (i in 0..6) {
            days[i] = format.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            Timber.e("Week ${days[i]}")
        }
        return days
    }

}