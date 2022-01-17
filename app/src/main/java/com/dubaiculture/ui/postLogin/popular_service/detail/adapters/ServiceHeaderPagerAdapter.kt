package com.dubaiculture.ui.postLogin.popular_service.detail.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dubaiculture.data.repository.popular_service.local.models.EServicesDetail
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.*

class ServiceHeaderPagerAdapter(
    fragment: Fragment,
    val eServicesDetail: EServicesDetail,
    val isFaqNull: Boolean = false,
    val isProcedureNull: Boolean = false,
    val isTermsNull: Boolean = false,
    val isPaymentNull: Boolean = false,
    val isRequirementNull: Boolean = false,
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = 6


    override fun createFragment(position: Int): Fragment {

        return when (position) {
            1 -> ProcedurePageFragment.newInstance(eServicesDetail.procedure)
            2 -> RequirementPageFragment.newInstance(eServicesDetail.requiredDocument)
            3 -> PaymentPageFragment.newInstance(eServicesDetail.payments)
            4 -> FaqsFragment.newInstance(eServicesDetail.fAQs)
            5 -> TermsAndConditionFragment.newInstance(eServicesDetail.termsAndCondition)
            else -> DescriptionPageFragment.newInstance(
                eServicesDetail.description,
                eServicesDetail.category
            )
        }
    }
}