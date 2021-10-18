package com.app.dubaiculture.ui.postLogin.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentPostCreatePassBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.login.viewmodel.PostCreatePassViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostCreatePassFragment : BaseFragment<FragmentPostCreatePassBinding>(), View.OnClickListener {
    private val postCreatePassViewModel: PostCreatePassViewModel by viewModels()
    private var verificationCode: String? = null
    private var from: String? = "postFragment"
    private var isHome: Boolean = false

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentPostCreatePassBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            verificationCode = it.getString("verificationCode")
            isHome = it.getBoolean("isHome")
            from = it.getString("post")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.viewmodel = postCreatePassViewModel
        lottieAnimationRTL(binding!!.animationView)
        subscribeUiEvents(postCreatePassViewModel)
        subscribeToObservable()
        backArrowRTL(binding!!.imgClose)

        binding!!.btnSetPassword.setOnClickListener {
            postCreatePassViewModel.setPassword(verificationCode)
        }
        binding!!.imgClose.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_close -> back()
        }

    }

    private fun subscribeToObservable() {
        postCreatePassViewModel.isPasswordSet.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                if (it) {
                    val bundle = bundleOf("post" to "postFragment")
                    if (!isHome) {
                        navigateByAction(
                            R.id.action_postCreatePassFragment_to_passwordUpdatedFragment2,
                            bundle
                        )
                    } else {
                        back()
                    }

                }
            }
        }
    }
}


