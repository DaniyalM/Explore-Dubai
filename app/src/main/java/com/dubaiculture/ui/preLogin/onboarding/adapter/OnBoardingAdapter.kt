package com.dubaiculture.ui.preLogin.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dubaiculture.ui.base.recyclerstuf.BaseFragmentStateAdapter
import com.dubaiculture.ui.preLogin.onboarding.steps.OnBoardingAFragment
import com.dubaiculture.ui.preLogin.onboarding.steps.OnBoardingBFragment
import com.dubaiculture.ui.preLogin.onboarding.steps.OnBoardingCFragment
import com.dubaiculture.ui.preLogin.onboarding.steps.OnBoardingDFragment

class OnBoardingAdapter (fragment : Fragment) : BaseFragmentStateAdapter<Any>(fragment) {
    override fun getItemCount() = 4
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                OnBoardingAFragment()
            }
            1 -> {
                OnBoardingBFragment()
            }
            2 -> {
                OnBoardingCFragment()
            }
            else -> {
                OnBoardingDFragment()
            }
        }
    }
}