package com.app.dubaiculture.ui.postLogin.more.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentLogoutBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.more.viewmodel.MoreSharedViewModel
import com.app.dubaiculture.ui.preLogin.login.viewmodels.LoginViewModel
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : BaseBottomSheetFragment<FragmentLogoutBinding>(), View.OnClickListener {


    private val loginViewModel: LoginViewModel by viewModels()
    private val moreSharedViewModel: MoreSharedViewModel by activityViewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLogoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener(this)
//        subscribeToObservable()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_logout -> {
                application.auth.user?.let {
                    loginViewModel.removeUser(it)
                    moreSharedViewModel._isLogged.value = Event(true)
                }

            }
            R.id.tv_cancel -> {
                back()
            }
        }
    }


//    private fun subscribeToObservable() {
//        loginViewModel.userLiveData.observe(viewLifecycleOwner) {
//            if (it != null) {
//
//            }
//        }
//
//    }

}