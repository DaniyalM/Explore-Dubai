package com.app.dubaiculture.ui.postLogin.popular_service.detail.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.dubaiculture.data.repository.popular_service.local.models.EServicesDetail
import com.app.dubaiculture.ui.postLogin.popular_service.detail.pages.*

class ServiceHeaderPagerAdapter(fragment: Fragment, val eServicesDetail: EServicesDetail) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = 6

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> ProcedurePageFragment(eServicesDetail.procedure)
            2 -> RequirementPageFragment(eServicesDetail.requiredDocument)
            3 -> PaymentPageFragment(eServicesDetail.payments)
            4 -> FaqsFragment(eServicesDetail.fAQs)
            5 -> TermsAndConditionFragment(eServicesDetail.termsAndCondition)
            else -> DescriptionPageFragment(eServicesDetail.description,eServicesDetail.category)
        }
    }
}