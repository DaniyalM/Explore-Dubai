package com.app.dubaiculture.ui.postLogin.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.databinding.FragmentEventsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.adapters.EventRecyclerAsyncAdapter
import com.app.dubaiculture.ui.postLogin.explore.adapters.ExploreRecyclerAsyncAdapter
import com.bumptech.glide.RequestManager
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import javax.inject.Inject

class EventsFragment:BaseFragment<FragmentEventsBinding>() {
    private lateinit var event: EventRecyclerAsyncAdapter
    @Inject
    lateinit var glide: RequestManager
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentEventsBinding.inflate(inflater,container,false)

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

    }
}