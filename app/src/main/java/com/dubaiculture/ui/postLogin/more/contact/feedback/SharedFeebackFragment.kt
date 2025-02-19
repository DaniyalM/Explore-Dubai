package com.dubaiculture.ui.postLogin.more.contact.feedback

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.dubaiculture.R
import com.dubaiculture.data.repository.more.local.FeedbacksType
import com.dubaiculture.databinding.FragmentSharedFeebackBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.more.contact.feedback.viewmodel.FeedbackViewModel
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.dialogs.ClickBack
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharedFeebackFragment : BaseFragment<FragmentSharedFeebackBinding>(),View.OnClickListener {
    private val feedbackViewModel : FeedbackViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSharedFeebackBinding.inflate(inflater, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(feedbackViewModel)
        binding.viewmodel = feedbackViewModel
        callingObserver()
        backArrowRTL(binding.imgClose)
        binding.imgClose.setOnClickListener(this)
        binding.spType.setOnClickListener{
            hideKeyboard(activity)
            binding.spType.showDropDown()
        }
        binding.spType.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.adapter.getItem(position) as FeedbacksType
            feedbackViewModel.selectedTypeId = selectedItem.id
//            feedbackViewModel.setSelectedType(selectedItem.id.toIntOrNull())
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close -> {
                back()
            }
        }
    }
    private fun callingObserver(){
        feedbackViewModel.sharedFeedBackType(getCurrentLanguage().language)
        feedbackViewModel.typeList.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                setTypeList(it)
            }
        }
    }

    @Subscribe
    fun doBack(clickBack: ClickBack) {
        when (clickBack) {
            is ClickBack.doBack -> {
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    back()
                }, 1000)

            }
        }

    }

    private fun setTypeList(list: List<FeedbacksType>) {
        val adapter = ArrayAdapter(
            activity,
            android.R.layout.simple_dropdown_item_1line,
            list
        )
        binding.spType.setAdapter(adapter)
    }
}