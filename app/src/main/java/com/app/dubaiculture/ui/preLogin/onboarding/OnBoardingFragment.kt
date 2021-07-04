package com.app.dubaiculture.ui.preLogin.onboarding

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.app.dubaiculture.databinding.FragmentOnBoardingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.preLogin.onboarding.adapter.OnBoardingAdapter
import com.app.dubaiculture.utils.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_on_boarding.*

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {
    private val context: Fragment = this
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerSetUp()
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentOnBoardingBinding.inflate(inflater,container,false)
    private fun viewPagerSetUp() {
//        Handler(Looper.getMainLooper()).postDelayed({ binding.wormDotsIndicator.refreshDots() }, 50)
        binding.apply {
            pager.adapter = OnBoardingAdapter(context)
            val zoomOutPageTransformer = ZoomOutPageTransformer()
            pager.setPageTransformer { page, position ->
                zoomOutPageTransformer.transformPage(page, position)
            }
            wormDotsIndicator.setViewPager2(pager)
        }
    }
}