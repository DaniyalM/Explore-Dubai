package com.dubaiculture.ui.postLogin.more.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentAboutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.more.viewmodel.MoreViewModel
import com.dubaiculture.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding>() , View.OnClickListener {
    private val moreViewModel : MoreViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(moreViewModel)
        binding.fragment = this
        backArrowRTL(binding.imgClose)
        binding.imgClose.setOnClickListener(this)
        binding.llPrivacy.setOnClickListener(this)
        binding.llTerms.setOnClickListener(this)

        moreViewModel.getCultureConnoisseur(getCurrentLanguage().language)
        callingObserver()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAboutBinding.inflate(inflater,container,false)

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close->{
                back()
            }
            R.id.ll_terms->{
                val bundle =
                        bundleOf(Constants.NavBundles.TERMS_CONDITION_PRIVACY_POLICY to Constants.NavBundles.TERMS_CONDITION)
                navigate(
                        R.id.action_aboutFragment_to_privacyTermConditionFragment,
                        bundle
                )
            }
            R.id.ll_privacy->{
                val bundle =
                        bundleOf(Constants.NavBundles.TERMS_CONDITION_PRIVACY_POLICY to Constants.NavBundles.PRIVACY_POLICY)
                navigate(
                        R.id.action_aboutFragment_to_privacyTermConditionFragment,
                        bundle
                )
            }
        }
    }

  private fun callingObserver(){
        moreViewModel.cultureCon.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {
                binding.cultureCon = it
            }
        }
    }

}