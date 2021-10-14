package com.app.dubaiculture.ui.preLogin.password

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.user.local.User
import com.app.dubaiculture.databinding.FragmentCreatePassBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.preLogin.password.viewModel.CreatePassViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CreatePassFragment : BaseFragment<FragmentCreatePassBinding>(), View.OnClickListener {
    private val createPassViewModel: CreatePassViewModel by viewModels()
    private var verificationCode: String? = null
    private var isHome:Boolean=false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            verificationCode = it.getString("verificationCode")
            isHome = it.getBoolean("isHome")
//            user=it.getParcelable<User>("user_object")
            Timber.e("TokenPass : $verificationCode")

        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = createPassViewModel
        lottieAnimationRTL(binding.animationView)
        subscribeUiEvents(createPassViewModel)
        subscribeToObservable()
        backArrowRTL(binding.imgClose)

        binding.btnSetPassword.setOnClickListener {
            createPassViewModel.setPassword(verificationCode)
        }
        binding.imgClose.setOnClickListener(this)
    }

    private fun subscribeToObservable(){
        createPassViewModel.isPasswordSet.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {
                if (it){
                    if (!isHome)
                    navigateByAction(R.id.action_createPassFragment_to_passwordUpdatedFragment)
                    else back()
                }
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCreatePassBinding.inflate(inflater, container, false)


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_close -> back()
        }

    }
}