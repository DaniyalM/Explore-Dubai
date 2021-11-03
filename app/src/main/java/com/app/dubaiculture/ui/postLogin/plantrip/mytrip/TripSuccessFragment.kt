package com.app.dubaiculture.ui.postLogin.plantrip.mytrip

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentTripSuccessBinding
import com.app.dubaiculture.ui.base.BaseDialogFragment
import com.app.dubaiculture.ui.base.BaseFragment


class TripSuccessFragment : BaseDialogFragment<FragmentTripSuccessBinding>() {

    private var from : String? = ""
    private var fromToPost : String? = ""

    override fun getTheme() = R.style.FullScreenDialog;

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripSuccessBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)
        lottieAnimationRTL(binding!!.animRegistration)

    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog?.window!!.apply {
                setLayout(width, height)
                @Suppress("DEPRECATION")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insetsController?.hide(WindowInsets.Type.statusBars())
                } else {
                    setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )
                }

            }

        }
    }



    fun onOkClicked(){
    }



}