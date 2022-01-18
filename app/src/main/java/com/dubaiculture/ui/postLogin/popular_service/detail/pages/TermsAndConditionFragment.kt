package com.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.RequiredDocument
import com.dubaiculture.data.repository.popular_service.local.models.TermsAndCondition
import com.dubaiculture.databinding.ItemServiceDetailTermsAndConditionLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.components.customreadmore.ReadMoreClickListener
import com.dubaiculture.ui.postLogin.popular_service.detail.ServiceDetailFragment
import com.dubaiculture.ui.postLogin.popular_service.detail.ServiceDetailFragmentDirections
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels.ServiceDownVoteFeedBackViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsAndConditionFragment: BaseFragment<ItemServiceDetailTermsAndConditionLayoutBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ItemServiceDetailTermsAndConditionLayoutBinding.inflate(inflater, container, false)


    private val serviceDownVoteFeedBackViewModel: ServiceDownVoteFeedBackViewModel by viewModels()
    private lateinit var termsAndCondition: List<TermsAndCondition>
    companion object {
        fun newInstance(termsAndCondition: List<TermsAndCondition>): TermsAndConditionFragment {
            val args = Bundle()
            args.putParcelableArrayList(Constants.NavBundles.TERMS_CONDITION_LIST, ArrayList(termsAndCondition))
            val fragment = TermsAndConditionFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            termsAndCondition= it.getParcelableArrayList(Constants.NavBundles.TERMS_CONDITION_LIST)!!
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(serviceDownVoteFeedBackViewModel)


        binding.detailListingHeader.text =
            activity.resources.getString(R.string.terms_and_conditions)
        binding.commonBtn.text = termsAndCondition!![0].serviceStart

        if (termsAndCondition[0].startServiceUrl.isEmpty())
            binding.commonBtn.hide()
        else
            binding.commonBtn.show()




        binding.commonBtn.setOnClickListener {

            openWebWithoutBaseUrl(termsAndCondition[0].startServiceUrl)
//            (parentFragment as ServiceDetailFragment).navigateByDirections(
//                ServiceDetailFragmentDirections.actionServiceDetailFragmentToWebViewFragment(
//                    termsAndCondition[0].startServiceUrl,
//                    false,
//                    termsAndCondition[0].termsAndConditionsTitle
//                )
//            )
        }


        binding.contactuslayout.thumbDown.setOnClickListener {

            if (application.auth.isGuest) {
                (parentFragment as ServiceDetailFragment).navigateByDirections(
                    ServiceDetailFragmentDirections.actionServiceDetailFragment2ToPostLoginBottomNavigation()
                )
            } else {
                (parentFragment as ServiceDetailFragment).navigateByDirections(
                    ServiceDetailFragmentDirections.actionServiceDetailFragment2ToServiceDownVoteFeedBackFragment(
                        termsAndCondition[0].id!!
                    )
                )
            }

        }

        termsAndCondition[0].apply {
            binding.contactuslayout.thumbUp.setOnClickListener {
                if (application.auth.isGuest) {
                    (parentFragment as ServiceDetailFragment).navigateByDirections(
                        ServiceDetailFragmentDirections.actionServiceDetailFragment2ToPostLoginBottomNavigation()
                    )
                } else {
                    serviceDownVoteFeedBackViewModel.upvoteService(id!!)
                }

            }

            termsAndConditionsSummary.let {
                if (!termsAndConditionsSummary.isEmpty()) {
                    binding.termsTitle.initialize(it, object : ReadMoreClickListener {
                        override fun onClick(readMore: Boolean) {

                        }
                    })
                } else {
                    binding.detailListingHeader.hide()
                    binding.termsTitle.hide()
                }

            }


            if (!emailAddress.toString().isNullOrEmpty()) {
                binding.contactuslayout.emailCallsBtn.llEmailUs.setOnClickListener {
                    showAlert(
                        title = "${resources.getString(R.string.confirm)}",
                        message = "${resources.getString(R.string.send_mail_text)} ${emailAddress.toString()}",
                        actionPositive = {
                            openEmailbox(emailAddress.toString())
                        },
                        textNegative = getString(R.string.no),
                        textPositive = getString(R.string.yes),
                        actionNegative = {

                        }

                    )
//                    openEmailbox(emailAddress.toString())
                }

            } else {
                binding.contactuslayout.emailCallsBtn.llEmailUs.isClickable = false
                binding.contactuslayout.emailCallsBtn.llEmailUs.alpha = 0.4f

            }
            if (!phoneNumber.isNullOrEmpty()) {
                binding.contactuslayout.emailCallsBtn.llCallus.setOnClickListener {
                    openDiallerBox(phoneNumber)
                }
            } else {
                binding.contactuslayout.emailCallsBtn.llCallus.isClickable = false
                binding.contactuslayout.emailCallsBtn.llCallus.alpha = 0.4f
            }


        }
    }


}