package com.dubaiculture.ui.postLogin.more.about

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.data.repository.more.local.Library
import com.dubaiculture.data.repository.trip.local.UsersType
import com.dubaiculture.databinding.FragmentAboutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.more.about.adapter.LibrariesAdapter
import com.dubaiculture.ui.postLogin.more.about.adapter.clicklisteners.LibraryClickListener
import com.dubaiculture.ui.postLogin.more.viewmodel.MoreViewModel
import com.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter.UserTypeAdapter
import com.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter.clicklisteners.UserTypeClickListener
import com.dubaiculture.utils.AppConfigUtils
import com.dubaiculture.utils.AppConfigUtils.EnglishToArabic
import com.dubaiculture.utils.AppConfigUtils.getDate
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.hide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding>(), View.OnClickListener {
    private val moreViewModel: MoreViewModel by viewModels()

    private lateinit var librariesAdapter: LibrariesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(moreViewModel)
        binding.fragment = this
        backArrowRTL(binding.imgClose)
        binding.imgClose.setOnClickListener(this)
        binding.llPrivacy.setOnClickListener(this)
        binding.llTerms.setOnClickListener(this)
//        binding.cardviewService.hide()
//        binding.tvWeUsed.hide()
        setupRV()
        getLibrariesWeUsed()
        moreViewModel.getCultureConnoisseur(getCurrentLanguage().language)
        callingObserver()
        try {
            val versionName =
                activity.packageManager.getPackageInfo(activity.packageName, 0).versionName
            getCurrentLanguage().language.let {
                if (it == "ar") {
                    binding.tvVersionNo.text =
                        "${resources.getString(R.string.version)}: ${EnglishToArabic(versionName)}"
                    binding.tvUpdatedDate.text = "${resources.getString(R.string.updated_on)}: ${
                        getDate(
                            BuildConfig.BUILD_TIME.time,
                            "dd-MM-yyyy",
                            getCurrentLanguage().language
                        )
                    }"
                } else {
                    binding.tvVersionNo.text =
                        "${resources.getString(R.string.version)}:$versionName"
                    binding.tvUpdatedDate.text = "${resources.getString(R.string.updated_on)}: ${
                        getDate(
                            BuildConfig.BUILD_TIME.time,
                            "dd-MM-yyyy",
                            getCurrentLanguage().language
                        )
                    }"

                }

            }

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun setupRV() {

        binding.rvLibraries.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            librariesAdapter = LibrariesAdapter(
                object : LibraryClickListener {
                    override fun rowClickListener(library: Library) {
//                        showToast(userType.title)
                        navigateByDirections(
                            AboutFragmentDirections.actionAboutFragmentToWebviewFragment(
                                library.url,
                                false,
                                library.name
                            )
                        )

                    }

                    override fun rowClickListener(library: Library, position: Int) {
                    }

                }
            )
            adapter = librariesAdapter


        }

    }

    private fun getLibrariesWeUsed() {

        var libraryList: MutableList<Library> = mutableListOf()

        libraryList.add(
            Library(
                "Retrofit",
                "https://github.com/square/retrofit/blob/master/LICENSE.txt"
            )
        )
        libraryList.add(Library("Glide", "https://github.com/bumptech/glide/blob/master/LICENSE"))
        libraryList.add(
            Library(
                "Lottie",
                "https://github.com/airbnb/lottie-android/blob/master/LICENSE"
            )
        )
        libraryList.add(
            Library(
                "Wikitude",
                "https://www.wikitude.com/documentation/latest/android/license.html#license-terms"
            )
        )
        libraryList.add(
            Library(
                "Groupie",
                "https://github.com/lisawray/groupie/blob/master/LICENSE.md"
            )
        )
        libraryList.add(
            Library(
                "Localization",
                "https://github.com/akexorcist/Localization/blob/master/LICENSE.txt"
            )
        )
        libraryList.add(
            Library(
                "LibPhoneNumber",
                "https://github.com/google/libphonenumber/blob/master/LICENSE"
            )
        )
        libraryList.add(
            Library(
                "Flexbox",
                "https://github.com/google/flexbox-layout/blob/main/LICENSE"
            )
        )
        libraryList.add(
            Library(
                "Estimote",
                "https://github.com/Estimote/Android-Proximity-SDK/blob/master/LICENSE"
            )
        )

        librariesAdapter.submitList(libraryList)


    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAboutBinding.inflate(inflater, container, false)

    fun onURLClicked(url: String, title: String) {

        navigateByDirections(
            AboutFragmentDirections.actionAboutFragmentToWebviewFragment(
                url,
                false,
                getString(R.string.about_dubai_culture_sl)
            )
        )

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_close -> {
                back()
            }
            R.id.ll_terms -> {
                val bundle =
                    bundleOf(Constants.NavBundles.TERMS_CONDITION_PRIVACY_POLICY to Constants.NavBundles.TERMS_CONDITION)
                navigate(
                    R.id.action_aboutFragment_to_privacyTermConditionFragment,
                    bundle
                )
            }
            R.id.ll_privacy -> {
                val bundle =
                    bundleOf(Constants.NavBundles.TERMS_CONDITION_PRIVACY_POLICY to Constants.NavBundles.PRIVACY_POLICY)
                navigate(
                    R.id.action_aboutFragment_to_privacyTermConditionFragment,
                    bundle
                )
            }
        }
    }

    private fun callingObserver() {
        moreViewModel.cultureCon.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                binding.cultureCon = it
            }
        }
    }

}