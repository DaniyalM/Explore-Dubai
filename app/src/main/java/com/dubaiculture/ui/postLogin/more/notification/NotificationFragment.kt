package com.dubaiculture.ui.postLogin.more.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.databinding.FragmentNotificationBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.more.notification.adapter.NotificationItems
import com.dubaiculture.ui.postLogin.more.notification.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding>(),
    NotificationItems.NotificationCounts {

    private val notificationViewModel: NotificationViewModel by viewModels()
    private lateinit var notificationListAdapter: NotificationItems


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNotificationBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(notificationViewModel)
        rvSetup()
        bgRTL(binding.imgClose)
        binding.imgClose.setOnClickListener {
            back()
        }
        subscribeToObservables()
    }

    private fun rvSetup() {
        notificationListAdapter = NotificationItems(this)
        binding.rvNotifications.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = notificationListAdapter
        }
    }

    private fun subscribeToObservables() {
        notificationViewModel.notificationPagination.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                notificationListAdapter.submitData(it)
            }

        }
    }

    override fun getNotificationCount(count: Int) {
        if(getCurrentLanguage()!= Locale.ENGLISH) {
            var nf: NumberFormat = NumberFormat.getInstance(Locale("ar"))
            binding.notificationCount.text = nf.format(count)
        }else{
            var nf: NumberFormat = NumberFormat.getInstance(Locale("en"))
            binding.notificationCount.text = nf.format(count)
        }
    }
}