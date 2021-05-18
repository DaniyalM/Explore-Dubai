package com.app.dubaiculture.ui.postLogin.more.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.app.dubaiculture.databinding.FragmentProfileBinding
import com.app.dubaiculture.ui.base.BaseFragment
import kotlinx.android.synthetic.main.layout_back.view.*

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentProfileBinding.inflate(inflater, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            header.back.setOnClickListener {
                back()
            }
            lottieAnimationRTL(animationView)
            backArrowRTL(header.back)
            backArrowRTLProfile(arrowFavourite)
            backArrowRTLProfile(arrowChangePassword)
            backArrowRTLProfile(arrowEvents)
            backArrowRTLProfile(arrowMyServices)
            backArrowRTLProfile(arrowMyTrips)
            backArrowRTLProfile(arrowPlacesVisited)
        }

    }

    fun backArrowRTLProfile(img: ImageView) {
        when {
            isArabic() -> {
                img.rotation = 180f
            }
            else -> {
                img.rotation = 0f
            }
        }
    }
}