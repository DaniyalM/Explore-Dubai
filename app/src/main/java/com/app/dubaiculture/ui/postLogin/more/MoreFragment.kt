package com.app.dubaiculture.ui.postLogin.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentMoreBinding
import com.app.dubaiculture.databinding.ItemsMoreLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.more.adapter.MoreItems
import com.app.dubaiculture.ui.postLogin.more.viewmodel.MoreViewModel
import com.google.android.material.shape.CornerFamily
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.plan_a_trip_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>(), View.OnClickListener {
    private val moreViewModel: MoreViewModel by viewModels()
    lateinit var newsAdapter: GroupAdapter<GroupieViewHolder>
    lateinit var settingAdapter: GroupAdapter<GroupieViewHolder>
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMoreBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(moreViewModel)
        moreViewModel.setupToolbarWithSearchItems(
            binding.root.profilePic,
            binding.root.img_drawer,
            binding.root.toolbar_title,
            resources.getString(R.string.more)
        )
        rvSetUp()
        cardViewRTL()
    }

    private fun cardViewRTL() {
        val radius = resources.getDimension(R.dimen.my_corner_radius_plan)
        binding.root.apply {
            tvTrip.text = resources.getString(R.string.plan_your_trip)
            subHeading.visibility = View.VISIBLE
            if (isArabic()) {
                cardivewRTL?.shapeAppearanceModel =
                    cardivewRTL.shapeAppearanceModel
                        .toBuilder()
                        .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                        .setTopRightCornerSize(radius)
                        .build()
            } else {
                cardivewRTL?.shapeAppearanceModel =
                    cardivewRTL.shapeAppearanceModel
                        .toBuilder()
                        .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                        .setBottomRightCornerSize(radius)
                        .build()
            }
        }
    }

    private fun rvSetUp() {
        newsAdapter = GroupAdapter()
        settingAdapter = GroupAdapter()
        moreViewModel.servicesList().map {
            groupAdapter.add(MoreItems<ItemsMoreLayoutBinding>(
                object : RowClickListener{
                    override fun rowClickListener(position: Int) {

                    }
                },moreModel = it , resLayout = R.layout.items_more_layout,requireContext()))
        }
        binding.rvServices.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
        }
        moreViewModel.newsList().map {
            newsAdapter.add(MoreItems<ItemsMoreLayoutBinding>(
                object : RowClickListener{
                    override fun rowClickListener(position: Int) {

                    }
                },moreModel = it , resLayout = R.layout.items_more_layout,requireContext()))
        }
        binding.rvNews.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }
        moreViewModel.settingsList().map {
            settingAdapter.add(MoreItems<ItemsMoreLayoutBinding>(
                object : RowClickListener{
                    override fun rowClickListener(position: Int) {

                    }
                },moreModel = it , resLayout = R.layout.items_more_layout,requireContext()))
        }
        binding.rvSettings.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = settingAdapter
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}