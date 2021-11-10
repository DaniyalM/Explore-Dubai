package com.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.TermsAndCondition
import com.dubaiculture.databinding.ItemServiceDetailTermsAndConditionLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.components.customreadmore.ReadMoreClickListener
import com.dubaiculture.ui.postLogin.popular_service.detail.ServiceDetailFragment
import com.dubaiculture.ui.postLogin.popular_service.detail.ServiceDetailFragmentDirections
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels.ServiceDownVoteFeedBackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsAndConditionFragment(
    val termsAndCondition: List<TermsAndCondition>?
) : BaseFragment<ItemServiceDetailTermsAndConditionLayoutBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ItemServiceDetailTermsAndConditionLayoutBinding.inflate(inflater, container, false)

    private val serviceDownVoteFeedBackViewModel: ServiceDownVoteFeedBackViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(serviceDownVoteFeedBackViewModel)
        binding.detailListingHeader.text =
            activity.resources.getString(R.string.terms_and_conditions)
        binding.commonBtn.text = termsAndCondition!![0].serviceStart
        binding.contactuslayout.thumbDown.setOnClickListener {
            (parentFragment as ServiceDetailFragment).navigateByDirections(
                ServiceDetailFragmentDirections.actionServiceDetailFragment2ToServiceDownVoteFeedBackFragment()
            )
        }
        binding.contactuslayout.thumbUp.setOnClickListener {
            serviceDownVoteFeedBackViewModel.upvoteService(termsAndCondition[0].id!!)
        }

        termsAndCondition.get(0).apply {
            termsAndConditionsSummary.let {
                binding.termsTitle.initialize(it, object : ReadMoreClickListener {
                    override fun onClick(readMore: Boolean) {

                    }
                })
            }
            if (!emailAddress.toString().isNullOrEmpty()) {
                binding.contactuslayout.emailCallsBtn.llEmailUs.setOnClickListener {
                    openEmailbox(emailAddress.toString())
                }

            } else
                binding.contactuslayout.emailCallsBtn.llEmailUs.isEnabled = false


            if (!phoneNumber.isNullOrEmpty()) {
                binding.contactuslayout.emailCallsBtn.llCallus.setOnClickListener {
                    openDiallerBox(phoneNumber)
                }
            } else
                binding.contactuslayout.emailCallsBtn.llCallus.isEnabled = false


        }
    }


}