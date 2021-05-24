package com.app.dubaiculture.ui.postLogin.events.detail.registernow

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentRegisterNowBinding
import com.app.dubaiculture.ui.base.BaseDialogFragment
import com.app.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_back.view.*
import kotlinx.android.synthetic.main.layout_back.view.back
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.*

@AndroidEntryPoint
class RegisterNowFragment : BaseDialogFragment<FragmentRegisterNowBinding>() , View.OnClickListener{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener(this)
        binding.root.back.setOnClickListener(this)
        backArrowRTL(binding.root.back)

    }

    override fun getTheme() = R.style.FullScreenDialog;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog)

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

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentRegisterNowBinding.inflate(inflater,container,false)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back->{
                back()
            }
            R.id.btn_submit->{
                val bundle = bundleOf(
                    "key" to "RegisterNow"
                )
                navigate(R.id.action_registerNowFragment_to_registrationSuccessFragment2, bundle)
            }
        }
    }
}