package com.app.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.TermsAndCondition
import com.app.dubaiculture.databinding.ItemServiceDetailTermsAndConditionLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.components.customreadmore.ReadMoreClickListener
import com.app.dubaiculture.ui.postLogin.popular_service.detail.ServiceDetailFragment
import com.app.dubaiculture.ui.postLogin.popular_service.detail.ServiceDetailFragmentDirections

class TermsAndConditionFragment(
    val termsAndCondition: List<TermsAndCondition>?
) : BaseFragment<ItemServiceDetailTermsAndConditionLayoutBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ItemServiceDetailTermsAndConditionLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailListingHeader.text =
            activity.resources.getString(R.string.terms_and_conditions)
        binding.commonBtn.text = termsAndCondition!![0].serviceStart
        binding.contactuslayout.thumbDown.setOnClickListener {
            (parentFragment as ServiceDetailFragment).navigateByDirections(
                ServiceDetailFragmentDirections.actionServiceDetailFragment2ToServiceDownVoteFeedBackFragment()
            )
        }

        termsAndCondition.get(0).apply {
            termsAndConditionsSummary.let {
                binding.termsTitle.initialize(it, object : ReadMoreClickListener {
                    override fun onClick(readMore: Boolean) {

                    }
                })
            }
            binding.contactuslayout.emailCallsBtn.llEmailUs.setOnClickListener {
                if (!emailAddress.toString().isNullOrEmpty()) {
                    openEmailbox(emailAddress.toString())
                }
            }

            binding.contactuslayout.emailCallsBtn.llCallus.setOnClickListener {
                if (!phoneNumber.isNullOrEmpty()) {
                    openDiallerBox(phoneNumber)
                }
            }
        }
    }
}