package com.app.dubaiculture.ui.postLogin.more.news.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.LatestNews
import com.app.dubaiculture.data.repository.notification.NotificationModel
import com.app.dubaiculture.ui.base.BaseViewModel

class NewsViewModel @ViewModelInject constructor(application: Application):BaseViewModel(application) {



    fun newsList(): ArrayList<LatestNews> {
        val list = ArrayList<LatestNews>()
        list.add(
            LatestNews(
               image =  "/-/media/DC/News-Detail/2.jpg",
                title = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution.",
                date = "12 Nov, 2020"
            )
        )

        list.add(
            LatestNews(
                image =  "/-/media/DC/News-Detail/2.jpg",
                title = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution.",
                date = "12 Nov, 2020"
            )
        )

        list.add(
            LatestNews(
                image =  "/-/media/DC/News-Detail/2.jpg",
                title = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution.",
                date = "12 Nov, 2020"
            )
        )

        list.add(
            LatestNews(
                image =  "/-/media/DC/News-Detail/2.jpg",
                title = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution.",
                date = "12 Nov, 2020"
            )
        )

        list.add(
            LatestNews(
                image =  "/-/media/DC/News-Detail/2.jpg",
                title = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution.",
                date = "12 Nov, 2020"
            )
        )
        return list
    }
}