package com.dubaiculture.ui.postLogin.popular_service.detail.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dubaiculture.data.repository.popular_service.local.models.EServicesDetail
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.*

class ServiceHeaderPagerAdapter(
    fragment: Fragment,
    val eServicesDetail: EServicesDetail,
    val forumPager: ViewPager2,
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
            1 -> ProcedurePageFragment(eServicesDetail.procedure)
            2 -> RequirementPageFragment(eServicesDetail.requiredDocument)
            3 -> PaymentPageFragment(eServicesDetail.payments)
            4 -> FaqsFragment(eServicesDetail.fAQs, forumPager)
            5 -> TermsAndConditionFragment(eServicesDetail.termsAndCondition)
            else -> DescriptionPageFragment(
                eServicesDetail.description,
                eServicesDetail.category
            )
        }
//        return if (isFaqNull && isTermsNull && isProcedureNull && isPaymentNull && isRequirementNull) {
//            return DescriptionPageFragment(
//                eServicesDetail.description,
//                eServicesDetail.category
//            )
//        }
//        else if (isFaqNull && isTermsNull && isProcedureNull && isPaymentNull) { when (position) {
//
//                1 -> RequirementPageFragment(eServicesDetail.requiredDocument)
//
//                else -> DescriptionPageFragment(
//                    eServicesDetail.description,
//                    eServicesDetail.category
//                )
//            } }
//        else if (isFaqNull && isTermsNull && isProcedureNull) { when (position) {
//
//                1 -> RequirementPageFragment(eServicesDetail.requiredDocument)
//                2 -> PaymentPageFragment(eServicesDetail.payments)
//
//                else -> DescriptionPageFragment(
//                    eServicesDetail.description,
//                    eServicesDetail.category
//                )
//            } }
//        else if (isFaqNull && isTermsNull) { when (position) {
//                1 -> ProcedurePageFragment(eServicesDetail.procedure)
//                2 -> RequirementPageFragment(eServicesDetail.requiredDocument)
//                3 -> PaymentPageFragment(eServicesDetail.payments)
//                else -> DescriptionPageFragment(
//                    eServicesDetail.description,
//                    eServicesDetail.category
//                )
//            } }
//        else {
//            if (isFaqNull) {
//                when (position) {
//                    1 -> ProcedurePageFragment(eServicesDetail.procedure)
//                    2 -> RequirementPageFragment(eServicesDetail.requiredDocument)
//                    3 -> PaymentPageFragment(eServicesDetail.payments)
//                    4 -> TermsAndConditionFragment(eServicesDetail.termsAndCondition)
//                    else -> DescriptionPageFragment(
//                        eServicesDetail.description,
//                        eServicesDetail.category
//                    )
//                }
//            } else if (isPaymentNull) { when (position) {
//                    1 -> ProcedurePageFragment(eServicesDetail.procedure)
//                    2 -> RequirementPageFragment(eServicesDetail.requiredDocument)
//                    3 -> FaqsFragment(eServicesDetail.fAQs, forumPager)
//                    4 -> TermsAndConditionFragment(eServicesDetail.termsAndCondition)
//                    else -> DescriptionPageFragment(
//                        eServicesDetail.description,
//                        eServicesDetail.category
//                    )
//                } }
//            else if (isProcedureNull) { when (position) {
//                    1 -> RequirementPageFragment(eServicesDetail.requiredDocument)
//                    2 -> PaymentPageFragment(eServicesDetail.payments)
//                    3 -> FaqsFragment(eServicesDetail.fAQs, forumPager)
//                    4 -> TermsAndConditionFragment(eServicesDetail.termsAndCondition)
//                    else -> DescriptionPageFragment(
//                        eServicesDetail.description,
//                        eServicesDetail.category
//                    )
//                } }
//            else if (isTermsNull) { when (position) {
//                    1 -> ProcedurePageFragment(eServicesDetail.procedure)
//                    2 -> RequirementPageFragment(eServicesDetail.requiredDocument)
//                    3 -> PaymentPageFragment(eServicesDetail.payments)
//                    4 -> FaqsFragment(eServicesDetail.fAQs, forumPager)
//                    else -> DescriptionPageFragment(
//                        eServicesDetail.description,
//                        eServicesDetail.category
//                    )
//                } }
//            else if (isRequirementNull) { when (position) {
//                    1 -> ProcedurePageFragment(eServicesDetail.procedure)
//                    2 -> PaymentPageFragment(eServicesDetail.payments)
//                    3 -> FaqsFragment(eServicesDetail.fAQs, forumPager)
//                    4 -> TermsAndConditionFragment(eServicesDetail.termsAndCondition)
//                    else -> DescriptionPageFragment(
//                        eServicesDetail.description,
//                        eServicesDetail.category
//                    )
//                } }
//            else { }
//        }


    }
}