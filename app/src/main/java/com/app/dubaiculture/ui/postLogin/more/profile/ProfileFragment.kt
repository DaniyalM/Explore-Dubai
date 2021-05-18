package com.app.dubaiculture.ui.postLogin.more.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.app.dubaiculture.databinding.FragmentProfileBinding
import com.app.dubaiculture.ui.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentProfileBinding.inflate(inflater, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lottieAnimationRTL(binding.animationView)
        backArrowRTL(binding.header.back)
        backArrowRTLProfile(binding.arrowFavourite)
        backArrowRTLProfile(binding.arrowChangePassword)
        backArrowRTLProfile(binding.arrowEvents)
        backArrowRTLProfile(binding.arrowMyServices)
        backArrowRTLProfile(binding.arrowMyTrips)
        backArrowRTLProfile(binding.arrowPlacesVisited)
    }

    fun backArrowRTLProfile(img: ImageView) {
        when {
            isArabic() -> {
                img.rotation = -180f
            }
            else -> {
                img.rotation = 90f
            }
        }
    }
}