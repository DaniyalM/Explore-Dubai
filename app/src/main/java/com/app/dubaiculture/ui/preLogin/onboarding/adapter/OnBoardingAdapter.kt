package com.app.dubaiculture.ui.preLogin.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.dubaiculture.ui.preLogin.onboarding.steps.OnBoardingAFragment
import com.app.dubaiculture.ui.preLogin.onboarding.steps.OnBoardingBFragment
import com.app.dubaiculture.ui.preLogin.onboarding.steps.OnBoardingCFragment
import com.app.dubaiculture.ui.preLogin.onboarding.steps.OnBoardingDFragment

class OnBoardingAdapter (fragment : Fragment) : FragmentStateAdapter(fragment) {
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
            3-> {
                OnBoardingDFragment()
            }
            else -> {
                OnBoardingAFragment()
            }
        }
    }
}