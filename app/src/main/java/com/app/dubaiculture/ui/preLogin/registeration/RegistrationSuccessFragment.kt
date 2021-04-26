package com.app.dubaiculture.ui.preLogin.registeration

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentCreatePassBinding
import com.app.dubaiculture.databinding.FragmentRegisterationSuccessBinding
import com.app.dubaiculture.ui.base.BaseDialogFragment
import com.app.dubaiculture.ui.base.BaseFragment


class RegistrationSuccessFragment : BaseDialogFragment<FragmentRegisterationSuccessBinding>(), View.OnClickListener{
    private var from : String? = ""
    private var fromToPost : String? = ""

    override fun getTheme() = R.style.FullScreenDialog;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)

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
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            from =    it.getString("key")
            fromToPost = it.getString("post")
        }

        if(from == "RegisterNow"){
            binding!!.regSuccessful.text = getString(R.string.register_confirm)
        }
        binding!!.btnContinueReg.setOnClickListener(this)

        binding!!.imgClose.setOnClickListener(this)
        lottieAnimationRTL(binding!!.animRegistration)
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentRegisterationSuccessBinding.inflate(inflater,container,false)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_continue_reg->{
                if(from == "RegisterNow"){
                    back()
                }else if(from == "postFragment"){
                    navigate(R.id.action_registrationSuccessFragment2_to_exploreFragment)
                }else
                navigate(R.id.action_registrationSuccessFragment_to_loginFragment)
            }
            R.id.img_close->{
                back()
            }
        }
    }
}