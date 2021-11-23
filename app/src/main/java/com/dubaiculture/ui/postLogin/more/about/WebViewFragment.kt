package com.dubaiculture.ui.postLogin.more.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentAboutBinding
import com.dubaiculture.databinding.FragmentWebViewBinding
import com.dubaiculture.ui.base.BaseFragment


class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {

    val args: WebViewFragmentArgs by navArgs()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWebViewBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        showToast(args.webviewUrl)

        binding.webview.loadUrl(args.webviewUrl)

        binding.imgClose.setOnClickListener {
            back()
        }

    }

}