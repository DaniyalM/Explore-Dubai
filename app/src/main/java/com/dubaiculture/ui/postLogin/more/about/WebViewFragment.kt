package com.dubaiculture.ui.postLogin.more.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.dubaiculture.databinding.FragmentWebViewBinding
import com.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {

    val args: WebViewFragmentArgs by navArgs()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWebViewBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backArrowRTL(binding.imgClose)
        binding.tvTitle.text = args.title
//        showToast(args.webviewUrl)
        binding.webview.settings.apply {

            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)

        }
        if (args.isPdf) {
            binding.webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + args.webviewUrl)
        } else {
            binding.webview.loadUrl(args.webviewUrl)
        }

        binding.imgClose.setOnClickListener {
            back()
        }

    }

}