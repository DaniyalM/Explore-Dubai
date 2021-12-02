package com.dubaiculture.ui.postLogin.popular_service.detail.pages.dialogs

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dubaiculture.databinding.FragmentServiceDownVoteBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels.ServiceDownVoteFeedBackViewModel
import com.squareup.otto.Subscribe
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
        serviceDownVoteFeedBackViewModel.locale = getCurrentLanguage().language
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

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservable()
    }

    private fun subscribeToObservable() {
        serviceDownVoteFeedBackViewModel.downVote.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it) {
//                    showAlert(title = "Feedback", message = "Feedback Submitted Successfully")
                    back()
                }
            }
        }
    }
}