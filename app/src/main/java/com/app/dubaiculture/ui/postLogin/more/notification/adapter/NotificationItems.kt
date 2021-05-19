package com.app.dubaiculture.ui.postLogin.more.notification.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.more.MoreModel
import com.app.dubaiculture.data.repository.notification.NotificationModel
import com.app.dubaiculture.databinding.ItemsNotitifcationsLayoutBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem

data class NotificationItems<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val notificationModel: NotificationModel,
    val resLayout: Int = R.layout.items_notitifcations_layout,
    val context: Context,
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemsNotitifcationsLayoutBinding -> {
                viewBinding.let {
                    it.tvNotiDesc.text = notificationModel.notificationTitle
                    it.tvTime.text = notificationModel.notificationTime
                    it.imgNotification.setImageResource(notificationModel.notificationImg!!)
                    it.rootView.setOnClickListener {

                    }
                }
            }
        }
    }

    override fun getLayout() = resLayout
}