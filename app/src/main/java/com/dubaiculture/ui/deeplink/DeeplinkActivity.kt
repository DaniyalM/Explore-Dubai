package com.dubaiculture.ui.deeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dubaiculture.R
import com.dubaiculture.ui.base.BaseActivity
import com.dubaiculture.ui.deeplink.viewmodel.DeepLinkViewModel
import com.dubaiculture.ui.postLogin.PostLoginActivity
import com.dubaiculture.ui.preLogin.PreLoginActivity
import com.dubaiculture.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import om.dubaiculture.ui.navGraphActivity.NavGraphActivity
import timber.log.Timber

@AndroidEntryPoint
class DeeplinkActivity: BaseActivity() {

    private val viewModel: DeepLinkViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link)

        lifecycleScope.launch {
            val user = viewModel.getUser()
            if (user == null && !applicationEntry.appStarted) {
                finish()
                startActivity(getPreLoginIntent())
            } else if (user == null && applicationEntry.appStarted) {
                finish()
            } else {
                applicationEntry.auth.user = user!!
                try {
                    handleDeepLink(intent?.data)
                } catch (exception: Exception) {
                    finish()
                }
            }
            applicationEntry.appStarted = true
        }
    }
    private fun handleDeepLink(data: Uri?) {
        data ?: return
        val uri = Uri.parse(data.toString())

        if (uri.path == "/attraction-detail") {
            val paramValue = uri.getQueryParameter("attraction_id")
//            val commentId = uri.getQueryParameter("commentId")
            val arr = paramValue?.split("-")
            val attraction_id = arr?.last()
            Timber.e(attraction_id)

            val mIntent = getNavGraphIntent()
            mIntent.putExtra(Constants.NavBundles.GRAPH_ID, R.navigation.attraction_detail_navigation)
            mIntent.putExtra(Constants.FCM.Key.ATTRACTION_ID, attraction_id?.toInt())
            startDeepLinkActivity(mIntent)
        } else if (uri.path == "/news-detail") {
            val paramValue = uri.getQueryParameter("news_id")
            val arr = paramValue?.split("-")
            val news_id = arr?.last()
            Timber.e(news_id)

            val mIntent = getNavGraphIntent()
            mIntent.putExtra(Constants.NavBundles.GRAPH_ID, R.navigation.news_detail_navigation)
            mIntent.putExtra(Constants.FCM.Key.NEWS_ID, news_id?.toInt())
            startDeepLinkActivity(mIntent)
        }else if (uri.path == "/event-detail") {
            val paramValue = uri.getQueryParameter("event_id")
            val arr = paramValue?.split("-")
            val event_id = arr?.last()
            Timber.e(event_id)

            val mIntent = getNavGraphIntent()
            mIntent.putExtra(Constants.NavBundles.GRAPH_ID, R.navigation.event_detail_navigation)
            mIntent.putExtra(Constants.FCM.Key.EVENT_ID, event_id?.toInt())
            startDeepLinkActivity(mIntent)
        }else if (uri.path == "/service-detail") {
            val paramValue = uri.getQueryParameter("service_id")
            val arr = paramValue?.split("-")
            val service_id = arr?.last()
            Timber.e(service_id)

//            val mIntent = getNavGraphIntent()
//            mIntent.putExtra(Constants.NavBundles.GRAPH_ID, R.navigation.service_detail_navigation)
//            mIntent.putExtra(Constants.FCM.Key.SERVICE_ID, service_id?.toInt())
//            startDeepLinkActivity(mIntent)
        }

    }


    private fun startDeepLinkActivity(mIntent: Intent) {
        if (!applicationEntry.appStarted)
            startActivity(
                Intent(
                    this,
                    PostLoginActivity::
                    class.java
                )
            )
        startActivity(mIntent)
        finish()
    }

    private fun getNavGraphIntent() = Intent(
        this,
        NavGraphActivity::
        class.java
    )

    private fun getPreLoginIntent() = Intent(
        this,
        PreLoginActivity::
        class.java
    )

}