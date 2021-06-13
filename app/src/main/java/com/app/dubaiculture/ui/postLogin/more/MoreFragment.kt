package com.app.dubaiculture.ui.postLogin.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentMoreBinding
import com.app.dubaiculture.databinding.ItemsMoreLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.more.adapter.MoreItems
import com.app.dubaiculture.ui.postLogin.more.viewmodel.MoreViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.PRIVACY_POLICY
import com.app.dubaiculture.utils.Constants.NavBundles.TERMS_CONDITION
import com.app.dubaiculture.utils.Constants.NavBundles.TERMS_CONDITION_PRIVACY_POLICY
import com.app.dubaiculture.utils.SettingsUtils.settingsList
import com.google.android.material.shape.CornerFamily
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.plan_a_trip_layout.view.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import java.util.*

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>(), View.OnClickListener {
    private val moreViewModel: MoreViewModel by viewModels()
    lateinit var newsAdapter: GroupAdapter<GroupieViewHolder>
    lateinit var settingAdapter: GroupAdapter<GroupieViewHolder>


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMoreBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(moreViewModel)
        binding.llRateUs.setOnClickListener(this)
        binding.llShareApp.setOnClickListener(this)
        binding.llNotification.setOnClickListener(this)
        binding.llCultureConnoisseur.setOnClickListener(this)
        moreViewModel.setupToolbarWithSearchItems(
            binding.root.profilePic,
            binding.root.img_drawer,
            binding.root.toolbar_title,
            resources.getString(R.string.more)
        )


        if (application.auth.isGuest) {
            binding.btnLogin.setOnClickListener {
                navigate(R.id.action_moreFragment_to_post_login_bottom_navigation)
            }
        } else {
            binding.btnLogin.visibility = View.GONE
            binding.user = application.auth.user
            binding.materialCardView2.setOnClickListener {
                navigate(R.id.action_moreFragment_to_profileFragment)
            }
        }
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
        if (groupAdapter.itemCount > 0) {
            groupAdapter.clear()
        }
        if (newsAdapter.itemCount > 0) {
            newsAdapter.clear()
        }
        if (settingAdapter.itemCount > 0) {
            settingAdapter.clear()
        }
        moreViewModel.servicesList().map {
            groupAdapter.add(
                MoreItems<ItemsMoreLayoutBinding>(
                    object : RowClickListener {
                        override fun rowClickListener(position: Int) {

                        }
                    },
                    moreModel = it,
                    resLayout = R.layout.items_more_layout,
                    requireContext(),
                    isArabic()
                )
            )
        }
        binding.rvServices.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
        }
        moreViewModel.newsList().map {
//            if (newsAdapter.itemCount > 0) {
//                newsAdapter.clear()
//            }
            newsAdapter.add(
                MoreItems<ItemsMoreLayoutBinding>(
                    object : RowClickListener {
                        override fun rowClickListener(position: Int) {
                            if (position == 0) {
                                navigate(R.id.action_moreFragment_to_latestNewsFragment)
                            }
                            if (position == 1) {
                                navigate(R.id.action_moreFragment_to_contactFragment)
                            }
                            if (position == 2) {
                                navigate(R.id.action_moreFragment_to_FAQsFragment)
                            }
                            if (position == 4) {
                                val bundle =
                                    bundleOf(TERMS_CONDITION_PRIVACY_POLICY to PRIVACY_POLICY)
                                navigate(
                                    R.id.action_moreFragment_to_privacyTermConditionFragment,
                                    bundle
                                )
                            }
                            if (position == 5) {
                                val bundle =
                                    bundleOf(TERMS_CONDITION_PRIVACY_POLICY to TERMS_CONDITION)
                                navigate(
                                    R.id.action_moreFragment_to_privacyTermConditionFragment,
                                    bundle
                                )

                            }
                        }
                    },
                    moreModel = it,
                    resLayout = R.layout.items_more_layout,
                    requireContext(),
                    isArabic()
                )
            )
        }
        binding.rvNews.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }
        settingsList(requireContext()).map {
            settingAdapter.add(
                MoreItems<ItemsMoreLayoutBinding>(
                    object : RowClickListener {
                        override fun rowClickListener(position: Int) {
                            if (position == 0) {
                                navigate(R.id.action_moreFragment_to_settingFragment)
                            }

                            if (position == 2) {
                                if (isArabic()) {
                                    setLanguage(Locale.ENGLISH)
                                } else {
                                    setLanguage(Locale("ar"))
                                }
                            }
                            if (position == 3) {
                                navigate(R.id.action_moreFragment_to_logoutFragment)
                            }
                        }
                    },
                    moreModel = it,
                    resLayout = R.layout.items_more_layout,
                    requireContext(),
                    isArabic(),
                    application.auth.isGuest
                )
            )
        }
        binding.rvSettings.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = settingAdapter
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_share_app -> {

            }
            R.id.ll_rate_us -> {

            }
            R.id.ll_notification -> {
                navigate(R.id.action_moreFragment_to_notificationFragment)
            }
            R.id.llCultureConnoisseur->{
                navigate(R.id.action_moreFragment_to_aboutFragment)

            }

        }
    }
}