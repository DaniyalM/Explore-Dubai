package com.dubaiculture.ui.preLogin.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentOnBoardingBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.preLogin.onboarding.adapter.OnBoardingAdapter
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.ZoomOutPageTransformer
import com.dubaiculture.utils.dataStore.DataKeys
import com.dubaiculture.utils.dataStore.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {
    @Inject
    lateinit var dataStoreManager: DataStoreManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerSetUp()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOnBoardingBinding.inflate(inflater, container, false)

    private fun viewPagerSetUp() {
        binding.pager.adapter = OnBoardingAdapter(this)
        binding.apply {
            val zoomOutPageTransformer = ZoomOutPageTransformer()
            pager.setPageTransformer { page, position ->
                zoomOutPageTransformer.transformPage(page, position)
            }
            wormDotsIndicator.setViewPager2(pager)

            pager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        3 -> {
                            binding.btnSkip.visibility = View.GONE
                            binding.getStarted.visibility = View.VISIBLE
                            binding.getStarted.setOnClickListener {
//                                lifecycleScope.launch {
//                                    dataStoreManager.setData(
//                                        preferencesKey(Constants.DataStore.SKIP),
//                                        true
//                                    )
//                                }
                                lifecycleScope.launch {
                                    dataStoreManager.setData(preferencesKey(Constants.DataStore.SKIP),true)
                                    navigate(R.id.action_onBoardingFragment2_to_loginFragment)
                                }

                            }
                        }
                        else -> {
                            binding.btnSkip.visibility = View.VISIBLE
                            binding.getStarted.visibility = View.GONE
                            binding.btnSkip.setOnClickListener {
//                                binding.pager.currentItem = position+1
                                lifecycleScope.launch {
                                    dataStoreManager.setData(preferencesKey(Constants.DataStore.SKIP),true)
                                    navigate(R.id.action_onBoardingFragment2_to_loginFragment)
                                }

                            }
                        }
                    }


                }
            })


        }
    }
}