package com.app.dubaiculture.ui.postLogin.events

//import q.rorbin.badgeview.QBadgeView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.request.EventRequest
import com.app.dubaiculture.databinding.FragmentEventFilterBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.components.EventHeaderItemSelector
import com.app.dubaiculture.ui.postLogin.events.filter.viewmodel.FilterViewModel
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.app.dubaiculture.utils.AppConfigUtils.clickCheckerFlag
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.dateFormat
import com.app.dubaiculture.utils.getDateObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.event_search_toolbar.view.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EventFilterFragment : BaseFragment<FragmentEventFilterBinding>(), View.OnClickListener {
    private val eventViewModel: EventViewModel by viewModels()
    private val filterViewModel: FilterViewModel by viewModels()
    private var eventList = mutableListOf<Events>()

    private var isContentLoaded = false
//    private var thisWeekList = mutableListOf<Events>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.root.img_filter.setOnClickListener(this)
        binding.root.back.setOnClickListener(this)
        subscribeUiEvents(eventViewModel)
        callingObservables()
        subscribeToObservables()
        initiatePager()
        QBadgeView(activity)
            .setBadgeBackgroundColor(R.color.colorPrimary)
            .bindTarget(binding!!.root.badge_placement).setBadgeNumber(5)
            .stroke(R.color.black_900, 6F, true)
            .setBadgeGravity(Gravity.START or Gravity.TOP)
            .setGravityOffset(18F, 6F, true)
    }

    private fun initiatePager() {
        binding.pager.isUserInputEnabled = false
//        binding.horizontalSelector.initialize(createItems(),
//            binding.pager,
//            this)
    }

    private fun callingObservables() {
        if (!isContentLoaded) {
            lifecycleScope.launch {
                eventViewModel.getFilterEventList(EventRequest(
                    culture = getCurrentLanguage().language
                ))
            }
        }

    }

    private fun subscribeToObservables() {
        eventViewModel.eventfilterRequest.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.let {
                        isContentLoaded = false
//                        eventList.addAll(it.value)
//                        thisWeekList = thisWeek(eventList)
                        binding.pager.isSaveEnabled = false

                        binding.horizontalSelector.initialize(createItems(),
                            binding.pager,
                            this)
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
        binding.horizontalSelector.positionUpdate(clickCheckerFlag)
    }

    private fun createItems(): List<HeaderModel> =
        mutableListOf<HeaderModel>().apply {
            repeat((1..4).count()) {
                when (it) {
                    0 -> {
                        add(
                            HeaderModel(
                                id = it,
                                title = getString(R.string.all),
                            ))
                    }
                    1 -> {
                        add(
                            HeaderModel(
                                id = it,
                                title = getString(R.string.this_week),
                            ))

                    }
                    2 -> {
                        add(
                            HeaderModel(
                                id = it,
                                title = getString(R.string.this_weekend),
                            ))

                    }
                    3 -> {
                        add(
                            HeaderModel(
                                id = it,
                                title = getString(R.string.next_seven_days),
                            ))
                    }


                }

            }
            isContentLoaded = true

        }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventFilterBinding.inflate(inflater, container, false)

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_filter -> {
                navigate(R.id.action_eventFilterFragment_to_filterFragment)
            }
            R.id.back -> {
                back()
            }
        }
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

    private fun getWeekend(list: List<Events>) {
        val weekendList = list.filter {
            it.toDay == "Friday" || it.toDay == "Satruday"
        }
    }

    private fun getNextSevenDays(list: List<Events>): Array<String?> {
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