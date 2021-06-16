package com.app.dubaiculture.ui.postLogin.more.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentNotificationBinding
import com.app.dubaiculture.databinding.ItemsNotitifcationsLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.more.notification.adapter.NotificationItems
import com.app.dubaiculture.ui.postLogin.more.notification.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {

    private val notificationViewModel: NotificationViewModel by viewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNotificationBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(notificationViewModel)
        rvSetup()
        backArrowRTL(binding.imgClose)
        binding.imgClose.setOnClickListener {
            back()
        }
    }

    private fun rvSetup() {
        notificationViewModel.notificationList().map {
            groupAdapter.add(
                NotificationItems<ItemsNotitifcationsLayoutBinding>(
                    notificationModel = it,
                    resLayout = R.layout.items_notitifcations_layout,
                    context = requireContext()
                )
            )

        }
        binding.rvNotifications.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
        }
    }
}