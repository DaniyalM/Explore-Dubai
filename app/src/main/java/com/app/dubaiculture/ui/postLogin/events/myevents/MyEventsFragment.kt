package com.app.dubaiculture.ui.postLogin.events.myevents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentPlacesVisitedBinding
import com.app.dubaiculture.databinding.ItemEventListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.app.dubaiculture.ui.postLogin.events.myevents.viewmodel.MyEventViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyEventsFragment : BaseFragment<FragmentPlacesVisitedBinding>() {
    private val myEventViewModel: MyEventViewModel by viewModels()
    var eventListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPlacesVisitedBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(myEventViewModel)
        myEventViewModel.getMyEvent(getCurrentLanguage().language)
        binding.apply {
            backArrowRTL(headerVisited.back)
            customTextView3.text = activity.resources.getString(R.string.my_events)
            headerVisited.back.setOnClickListener {
                back()
            }
            personalRv.apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = eventListAdapter
            }
            subscribeObserver()

        }

    }
private fun subscribeObserver() {
    myEventViewModel.myEvents.observe(viewLifecycleOwner) {
        it.map {
            eventListAdapter.add(
                EventListItem<ItemEventListingBinding>(
                    object :
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
                                R.id.action_eventsFragment_to_postLoginFragment,
                                itemId,
                                myEventViewModel
                            )
                        }

                    },
                    object : RowClickListener {
                        override fun rowClickListener(position: Int) {
//                                val eventObj = allList[position]
//                                val bundle = Bundle()
//                                bundle.putParcelable(Constants.NavBundles.EVENT_OBJECT, eventObj)
//                                navigate(
//                                    R.id.action_eventFilterFragment_to_eventDetailFragment2,
//                                    bundle)
                        }

                        override fun rowClickListener(position: Int, imageView: ImageView) {

                        }

                    },
                    event = it,
                    resLayout = R.layout.item_event_listing,
                    activity,
                    hasSurvey = true
                )
            )

        }

    }
}
}