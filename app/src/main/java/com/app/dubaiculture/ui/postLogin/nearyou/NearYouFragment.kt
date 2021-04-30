package com.app.dubaiculture.ui.postLogin.nearyou

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentNearYouBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.nearyou.adapter.NearToYouItems


class NearYouFragment : BaseBottomSheetFragment<FragmentNearYouBinding>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerViewSetup()
        androidx.core.view.ViewCompat.setNestedScrollingEnabled(binding.rvMuseum, false)
        binding.rvMuseum.setOnTouchListener(OnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            v.onTouchEvent(event)
            true
        })
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentNearYouBinding.inflate(inflater, container, false)


    private fun recyclerViewSetup() {
        binding.rvMuseum.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
            groupAdapter.apply {
                add(NearToYouItems(R.drawable.must_see_icon_home,
                    "Al Fahidi Historical Neighbourhood",
                    "1 Jumeirah St - Al\nMina Dubai",
                    "10 Km"))
                add(NearToYouItems(R.drawable.must_see_icon_home,
                    "Al Fahidi Historical Neighbourhood",
                    "1 Jumeirah St - Al\nMina Dubai",
                    "10 Km"))
                add(NearToYouItems(R.drawable.must_see_icon_home,
                    "Al Fahidi Historical Neighbourhood",
                    "1 Jumeirah St - Al\nMina Dubai",
                    "10 Km"))
                add(NearToYouItems(R.drawable.must_see_icon_home,
                    "Al Fahidi Historical Neighbourhood",
                    "1 Jumeirah St - Al\nMina Dubai",
                    "10 Km"))
                add(NearToYouItems(R.drawable.must_see_icon_home,
                    "Al Fahidi Historical Neighbourhood",
                    "1 Jumeirah St - Al\nMina Dubai",
                    "10 Km"))
                add(NearToYouItems(R.drawable.must_see_icon_home,
                    "Al Fahidi Historical Neighbourhood",
                    "1 Jumeirah St - Al\nMina Dubai",
                    "10 Km"))
            }
        }
    }
}