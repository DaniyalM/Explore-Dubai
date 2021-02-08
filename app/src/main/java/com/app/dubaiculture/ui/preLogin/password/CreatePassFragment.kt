package com.app.dubaiculture.ui.preLogin.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentCreatePassBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.preLogin.password.passwordupdated.PasswordUpdatedFragment
import com.app.dubaiculture.ui.preLogin.password.viewModel.CreatePassViewModel
import kotlinx.android.synthetic.main.fragment_create_pass.view.*

class CreatePassFragment : BaseFragment<FragmentCreatePassBinding>(),View.OnClickListener{
    private var modalDismissWithAnimation = false
    private val createPassViewModel: CreatePassViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnSetPass.btn.setOnClickListener { showModalBottomSheet() }
        binding.imgClose.setOnClickListener(this)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentCreatePassBinding.inflate(inflater,container,false)


    private fun showModalBottomSheet() {
        val modalBottomSheet = PasswordUpdatedFragment.newInstance(modalDismissWithAnimation)
        modalBottomSheet.show(requireActivity().supportFragmentManager, PasswordUpdatedFragment.TAG)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close-> back()
        }

    }
}