package com.app.dubaiculture.ui.preLogin.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentOnBoardingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.preLogin.onboarding.adapter.OnBoardingAdapter
import com.app.dubaiculture.utils.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerSetUp()
    }

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentOnBoardingBinding.inflate(inflater, container, false)

    private fun viewPagerSetUp() {
//        Handler(Looper.getMainLooper()).postDelayed({ binding.wormDotsIndicator.refreshDots() }, 50)
        binding.pager.adapter = OnBoardingAdapter(this)
//        binding.pager.isUserInputEnabled = false
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
                                application.auth.isLoggedIn = true
                                navigate(R.id.action_onBoardingFragment_to_LoginFragment)
                            }
                        }
                        else -> {
                            binding.btnSkip.visibility = View.VISIBLE
                            binding.getStarted.visibility = View.GONE
                            binding.btnSkip.setOnClickListener {
                                pager.currentItem += 1
                            }
                        }
                    }


                }
            })


        }
    }
}