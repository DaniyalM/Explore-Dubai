package com.app.dubaiculture.ui.postLogin.more

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import com.app.dubaiculture.utils.Constants.NavBundles.MORE_FRAGMENT
import com.app.dubaiculture.utils.Constants.NavBundles.PRIVACY_POLICY
import com.app.dubaiculture.utils.Constants.NavBundles.SETTING_DESTINATION
import com.app.dubaiculture.utils.Constants.NavBundles.TERMS_CONDITION
import com.app.dubaiculture.utils.Constants.NavBundles.TERMS_CONDITION_PRIVACY_POLICY
import com.app.dubaiculture.utils.SettingsUtils.newsList
import com.app.dubaiculture.utils.SettingsUtils.servicesList
import com.app.dubaiculture.utils.SettingsUtils.settingsList
import com.google.android.material.shape.CornerFamily
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>(), View.OnClickListener {
    private val moreViewModel: MoreViewModel by viewModels()
    lateinit var newsAdapter: GroupAdapter<GroupieViewHolder>
    lateinit var settingAdapter: GroupAdapter<GroupieViewHolder>
    var navigateSettings = false

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMoreBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
           navigateSettings = it.getBoolean(SETTING_DESTINATION)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (navigateSettings) {
            navigateSettings=false
            navigate(R.id.action_moreFragment_to_settingFragment)
        }
        subscribeUiEvents(moreViewModel)
        bgAboutRTL(binding.imgEagle)
        binding.llRateUs.setOnClickListener(this)
        binding.llShareApp.setOnClickListener(this)
        binding.llNotification.setOnClickListener(this)
        binding.llCultureConnoisseur.setOnClickListener(this)
        moreViewModel.setupToolbarWithSearchItems(
            binding.toolbarSnippet.toolbarLayout.profilePic,
            binding.toolbarSnippet.toolbarLayout.imgDrawer,
            binding.toolbarSnippet.toolbarLayout.toolbarTitle,
            resources.getString(R.string.more)
        )

        if (application.auth.isGuest) {
            binding.btnLogin.setOnClickListener {
                val bundle = bundleOf(MORE_FRAGMENT to MORE_FRAGMENT)
                navigate(R.id.action_moreFragment_to_post_login_bottom_navigation, bundle)
            }
            binding.materialCardView2.setOnClickListener(null)
        } else {
            binding.user = application.auth.user
            binding.title.text = application.auth.user?.userName
                ?: resources.getString(R.string.my_profile)
            if (binding.title.text.length >= 15) {
                binding.btnLogin.visibility = View.GONE
            } else {
                binding.btnLogin.visibility = View.INVISIBLE
            }
            binding.materialCardView2.setOnClickListener {
                navigate(R.id.action_moreFragment_to_profileFragment)
            }
        }
        rvSetUp()
        cardViewRTL()

    }

    private fun cardViewRTL() {
        val radius = resources.getDimension(R.dimen.my_corner_radius_plan)
        binding.planATripLayout.apply {
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
        servicesList(activity).map {
            groupAdapter.add(
                MoreItems<ItemsMoreLayoutBinding>(
                    object : RowClickListener {
                        override fun rowClickListener(position: Int) {

                        }

                        override fun rowClickListener(position: Int, imageView: ImageView) {

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
        newsList(activity).map {
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
                            if (position == 3) {
                                moreViewModel.playStoreOpen(activity)
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

                        override fun rowClickListener(position: Int, imageView: ImageView) {

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
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }
        settingsList(activity).map {
            settingAdapter.add(
                MoreItems<ItemsMoreLayoutBinding>(
                    object : RowClickListener {
                        override fun rowClickListener(position: Int) {
                            if (position == 0) {
                                if (application.auth.isGuest) {
                                    navigate(R.id.action_moreFragment_to_post_login_bottom_navigation)
                                } else {
                                    navigate(R.id.action_moreFragment_to_settingFragment)
                                }
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

                        override fun rowClickListener(position: Int, imageView: ImageView) {

                        }
                    },
                    moreModel = it,
                    resLayout = R.layout.items_more_layout,
                    activity,
                    isArabic(),
                    application.auth.isGuest
                )
            )
        }
        binding.rvSettings.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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
            R.id.llCultureConnoisseur -> {
                navigate(R.id.action_moreFragment_to_aboutFragment)

            }

        }
    }
}