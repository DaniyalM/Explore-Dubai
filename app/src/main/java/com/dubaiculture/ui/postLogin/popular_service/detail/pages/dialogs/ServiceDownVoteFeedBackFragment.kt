package com.dubaiculture.ui.postLogin.popular_service.detail.pages.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dubaiculture.databinding.FragmentServiceDownVoteBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels.ServiceDownVoteFeedBackViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ServiceDownVoteFeedBackFragment : BaseFragment<FragmentServiceDownVoteBinding>() {

    private val serviceDownVoteFeedBackViewModel: ServiceDownVoteFeedBackViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentServiceDownVoteBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = serviceDownVoteFeedBackViewModel
//        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)
        binding.header.back.apply {
            setOnClickListener {
                back()
            }
            backArrowRTL(this)
        }
        subscribeUiEvents(serviceDownVoteFeedBackViewModel)

    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservable()
    }

    private fun subscribeToObservable() {
        serviceDownVoteFeedBackViewModel.downVote.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it) {
                    showAlert(title = "Feedback", message = "Feedback Submitted Successfully")
                    back()
                }
            }
        }
    }
}