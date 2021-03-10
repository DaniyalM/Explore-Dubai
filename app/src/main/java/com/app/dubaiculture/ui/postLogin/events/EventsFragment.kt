package com.app.dubaiculture.ui.postLogin.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentEventsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.adapters.EventRecyclerAsyncAdapter
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import javax.inject.Inject

@AndroidEntryPoint
class EventsFragment : BaseFragment<FragmentEventsBinding>() {
    private lateinit var event: EventRecyclerAsyncAdapter

    @Inject
    lateinit var glide: RequestManager
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventsBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRv()
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false

        }

    }

    private fun setUpRv() {
        event = EventRecyclerAsyncAdapter(activity)
        binding.rvEvent.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(activity)
            adapter = event
            //  event.provideGlideInstance(glide)
            this.itemAnimator = SlideInLeftAnimator()
        }
        event.items = createTestItems()

    }


    private fun createTestItems(): List<EventHomeListing> =
        mutableListOf<EventHomeListing>().apply {


            repeat((1..2).count()) {
                when (it % 2){
                    0->{
                        add(
                            EventHomeListing(
                                title = "FeatureEvents",
                                category = "FeatureEvents",
                                events = createAttractionItems()
                            )
                        )
                    }
                    else->{
                        add(
                            EventHomeListing(
                                title = "More Events",
                                category = "MoreEvents",
                                events = createAttractionItems()
                            )
                        )
                    }
                }


            }
        }

    private fun createAttractionItems(): ArrayList<Events> = mutableListOf<Events>().apply {


        repeat((1..4).count()) {

            add(
                Events(
                    id = it.toString(),
                    title = "Title $it",
                    category = "Category $it",
                    fromDate = "18",
                    fromMonthYear = "Mar, 21",
                    fromTime = "201$it",
                    fromDay = "1$it",
                    toDate = "$it",
                    toMonthYear = "Mar, 21",
                    toTime = "202$it",
                    toDay = "2$it",
                    type ="Free"
                )
            )
        }
    } as ArrayList<Events>
}