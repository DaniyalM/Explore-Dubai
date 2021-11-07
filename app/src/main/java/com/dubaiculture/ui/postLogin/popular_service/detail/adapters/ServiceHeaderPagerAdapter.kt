package com.dubaiculture.ui.postLogin.popular_service.detail.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dubaiculture.data.repository.popular_service.local.models.EServicesDetail
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.*

class ServiceHeaderPagerAdapter(
    fragment: Fragment,
    val eServicesDetail: EServicesDetail,
    val forumPager: ViewPager2
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = 6

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> ProcedurePageFragment(eServicesDetail.procedure)
            2 -> RequirementPageFragment(eServicesDetail.requiredDocument)
            3 -> PaymentPageFragment(eServicesDetail.payments)
            4 -> FaqsFragment(eServicesDetail.fAQs, forumPager)
            5 -> TermsAndConditionFragment(eServicesDetail.termsAndCondition)
            else -> DescriptionPageFragment(eServicesDetail.description, eServicesDetail.category)
        }
    }
}