package com.app.dubaiculture.ui.postLogin.more.notification.viewmodel

import android.app.Application
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.notification.NotificationModel
import com.app.dubaiculture.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(application: Application) :
    BaseViewModel(application) {


    fun notificationList(): ArrayList<NotificationModel> {
        val list = ArrayList<NotificationModel>()
        list.add(
            NotificationModel(
                R.drawable.notification_heritage,
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution.",
                "1 hr ago"
            )
        )
        list.add(
            NotificationModel(
                R.drawable.notification_heritage,
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution.",
                "4 hr ago"
            )
        )
        list.add(
            NotificationModel(
                R.drawable.notification_heritage,
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution.",
                "3 hr ago"
            )
        )
        list.add(
            NotificationModel(
                R.drawable.notification_heritage,
                "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution.",
                "2 hr ago"
            )
        )

        return list
    }

}