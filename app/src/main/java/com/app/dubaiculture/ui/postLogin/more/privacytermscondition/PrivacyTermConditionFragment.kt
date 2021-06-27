package com.app.dubaiculture.ui.postLogin.more.privacytermscondition

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentPrivacyTermConditionBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.more.viewmodel.MoreViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.TERMS_CONDITION
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyTermConditionFragment : BaseBottomSheetFragment<FragmentPrivacyTermConditionBinding>() ,View.OnClickListener{
    private val moreViewModel : MoreViewModel by viewModels()
    private var from : String?=null
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentPrivacyTermConditionBinding.inflate(inflater,container,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(moreViewModel)
        backArrowRTL(binding.imgClose)
        arguments?.let {
            from = it.getString(Constants.NavBundles.TERMS_CONDITION_PRIVACY_POLICY).toString()
        }
        binding.imgClose.setOnClickListener(this)
        callingObserver()
    }
    private fun callingObserver(){
        if(from == TERMS_CONDITION){
            moreViewModel.termsCondition(getCurrentLanguage().language)

            moreViewModel.termsCondition.observe(viewLifecycleOwner){
                it.getContentIfNotHandled()?.let {
                    binding.title.text = it.title
                    binding.tvDesc.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        Html.fromHtml(it.description)
                    }
                }
            }
        } else{
            moreViewModel.privacyPolicy(getCurrentLanguage().language)
            moreViewModel.privacyPolice.observe(viewLifecycleOwner){
                it.getContentIfNotHandled()?.let {
                    binding.title.text = it.title
                    binding.tvDesc.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        Html.fromHtml(it.description)
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close->{
                dismiss()
            }
        }
    }

}