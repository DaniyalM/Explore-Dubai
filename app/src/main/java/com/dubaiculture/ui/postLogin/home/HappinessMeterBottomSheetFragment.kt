package com.dubaiculture.ui.postLogin.home

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentHappinessMeterBottomSheetBinding
import com.dubaiculture.happiness.HappinessMeter
import com.dubaiculture.happiness.Type
import com.dubaiculture.ui.base.BaseBottomSheetFragment


class HappinessMeterBottomSheetFragment :
    BaseBottomSheetFragment<FragmentHappinessMeterBottomSheetBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHappinessMeterBottomSheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HappinessMeter.load(
            Type.WITHOUT_MICROAPP,
            binding.webView,
            application.auth.locale.toString()
        )

        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView, url: String) {

                if (url.contains("happiness://done")) {
                    dismiss()
//                    binding.flWebview.visibility = View.GONE
//                    navigateByDirections(TripSuccessFragmentDirections.actionTripSuccessToMySaveTripListing())
                }

            }
        }

    }


}