package com.dubaiculture.ui.postLogin.more

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.ServiceCategory
import com.dubaiculture.databinding.FragmentMoreBinding
import com.dubaiculture.databinding.ItemsMoreLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.ui.postLogin.more.adapter.MoreItems
import com.dubaiculture.ui.postLogin.more.adapter.ServicesAdapter
import com.dubaiculture.ui.postLogin.more.adapter.clicklisteners.ServicesClickListener
import com.dubaiculture.ui.postLogin.more.viewmodel.MoreViewModel
import com.dubaiculture.utils.AppConfigUtils.getDate
import com.dubaiculture.utils.Constants.NavBundles.MORE_FRAGMENT
import com.dubaiculture.utils.Constants.NavBundles.PRIVACY_POLICY
import com.dubaiculture.utils.Constants.NavBundles.SERVICE_ID
import com.dubaiculture.utils.Constants.NavBundles.SERVICE_POS
import com.dubaiculture.utils.Constants.NavBundles.TERMS_CONDITION
import com.dubaiculture.utils.Constants.NavBundles.TERMS_CONDITION_PRIVACY_POLICY
import com.dubaiculture.utils.SettingsUtils.newsList
import com.dubaiculture.utils.SettingsUtils.servicesList
import com.dubaiculture.utils.SettingsUtils.settingsList
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.shape.CornerFamily
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>(), View.OnClickListener {
    private val moreViewModel: MoreViewModel by viewModels()
    lateinit var newsAdapter: GroupAdapter<GroupieViewHolder>
    lateinit var settingAdapter: GroupAdapter<GroupieViewHolder>
    var moreListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private lateinit var serviceAdapter: ServicesAdapter

    @Inject
    lateinit var glide: RequestManager

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMoreBinding.inflate(inflater, container, false)

    private fun subscribeToObservable() {
        moreViewModel.notificationCount.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {

                val count = if (getCurrentLanguage().language == "en") String.format(
                    "%02d",
                    it.toInt()
                ) else it.toInt()

                if (it.toInt() > 0)
                    binding.notiCount.text = "${count} new "
                else
                    binding.notiCount.text = "${0} new"
            }
        }

        moreViewModel.serviceListCategory.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                serviceAdapter.submitList(it)
            }
        }

    }

    private fun setupRV() {

        binding.rvServices.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            serviceAdapter = ServicesAdapter(
                object : ServicesClickListener {
                    override fun rowClickListener(serviceCategory: ServiceCategory) {
//                        showToast(userType.title)


                    }

                    override fun rowClickListener(serviceCategory: ServiceCategory, position: Int) {

                        val bundle = Bundle()
                        bundle.putString(
                            SERVICE_ID,
                            serviceCategory.id
                        )
                        bundle.putInt(
                            SERVICE_POS,
                            position
                        )
                        navigate(R.id.action_moreFragment_to_popularServiceFragment2, bundle)

                    }

                },
                glide
            )
            adapter = serviceAdapter


        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        moreViewModel.getEServicesToScreen(application.auth.locale.toString())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notiCount.text = "${0} new"

        subscribeUiEvents(moreViewModel)
        moreViewModel.notificationCount(getCurrentLanguage().language)
        subscribeToObservable()
        bgAboutRTL(binding.imgEagle)
        binding.toolbarSnippet.toolbarLayout.search.setOnClickListener(this)
        binding.llRateUs.setOnClickListener(this)
        binding.llShareApp.setOnClickListener(this)
        binding.llNotification.setOnClickListener(this)
        binding.llCultureConnoisseur.setOnClickListener(this)
        binding.planATripLayout.cardivewRTL.setOnClickListener(this)
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
        setupRV()
        cardViewRTL()
        try {
            val versionName = activity.packageManager.getPackageInfo(activity.packageName, 0).versionName
            getCurrentLanguage().language.let {
                if (it=="ar"){
                    binding.tvVersionNo.text = "${resources.getString(R.string.version)}: ${EnglishToArabic(versionName)}"
                    binding.tvUpdatedDate.text ="${resources.getString(R.string.updated_on)}: ${getDate(BuildConfig.BUILD_TIME.time, "dd-mm-yyyy",getCurrentLanguage().language)}"
                }else {
                    binding.tvVersionNo.text = "${resources.getString(R.string.version)}:$versionName"
                    binding.tvUpdatedDate.text ="${resources.getString(R.string.updated_on)}: ${getDate(BuildConfig.BUILD_TIME.time, "dd-mm-yyyy",getCurrentLanguage().language)}"

                }

            }

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    fun EnglishToArabic(str: String):String {
        var result = ""
        var ar = '۰'
        for (ch in str) {
            ar = ch
            when (ch) {
                '0'  -> ar = '۰'
                '1'-> ar =  '۱'
                '2' -> ar ='۲'
                '3' -> ar ='۳'
                '4'-> ar =  '۴'
                '5'-> ar ='۵'
                '6' -> ar = '۶'
                '7'-> ar = '۷'
                '8'-> ar = '۸'
                '9' -> ar = '۹'
            }
            result = "${result}$ar"
        }
        return result
    }

    private fun cardViewRTL() {
        val radius = resources.getDimension(R.dimen.my_corner_radius_plan)
        binding.planATripLayout.apply {
            tvTrip.text = resources.getString(R.string.plan_your_trip)
            subHeading.visibility = View.VISIBLE
            if (isArabic()) {
                cardivewRTL.shapeAppearanceModel =
                    cardivewRTL.shapeAppearanceModel
                        .toBuilder()
                        .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                        .setTopRightCornerSize(radius)
                        .build()
            } else {
                cardivewRTL.shapeAppearanceModel =
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
        if (moreListAdapter.itemCount > 0) {
            moreListAdapter.clear()
        }
        if (newsAdapter.itemCount > 0) {
            newsAdapter.clear()
        }
        if (settingAdapter.itemCount > 0) {
            settingAdapter.clear()
        }
        servicesList(activity).map {
            moreListAdapter.add(
                MoreItems<ItemsMoreLayoutBinding>(
                    object : RowClickListener {
                        override fun rowClickListener(position: Int) {
                            when (position) {
                                0 -> {

                                    navigate(R.id.action_moreFragment_to_popularServiceFragment2)
                                }

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
        binding.rvServices.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = moreListAdapter
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
                                navigateByDirections(
                                    MoreFragmentDirections.actionMoreFragmentToLatestNewsFragment(
                                        getCurrentLanguage().language
                                    )
                                )

                            }
                            if (position == 1) {
                                navigateByDirections(
                                    MoreFragmentDirections.actionMoreFragmentToContactFragment()
                                )
                            }
                            if (position == 2) {
                                navigateByDirections(
                                    MoreFragmentDirections.actionMoreFragmentToFAQsFragment()
                                )
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
                            if (position == 1) {
                                showAccessibilityDialog()
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

    private fun showAccessibilityDialog() {

        MaterialAlertDialogBuilder(context!!, R.style.MaterialDialogTheme)
            .setMessage(resources.getString(R.string.accessbility_desc))
            .setPositiveButton(resources.getString(R.string._ok)) { dialog, which ->
                dialog.dismiss()
            }
            .show()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_share_app -> {
                moreViewModel.shareAppLink(activity)
            }
            R.id.ll_rate_us -> {
                moreViewModel.rateUs(activity)
            }
            R.id.ll_notification -> {
                navigate(R.id.action_moreFragment_to_notificationFragment)
            }
            R.id.llCultureConnoisseur -> {
                navigate(R.id.action_moreFragment_to_aboutFragment)
            }
            R.id.cardivewRTL -> {
                if (!application.auth.isGuest)
                    navigateByDirections(MoreFragmentDirections.actionMoreFragmentToTripFragment())
//                navigate(R.id.action_moreFragment_to_tripFragment)
            }
            R.id.search -> {
                navigateByDirections(MoreFragmentDirections.actionMoreFragmentToSearchNavigation())
            }

        }
    }
}