package com.app.dubaiculture.ui.postLogin.more.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentLogoutBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.explore.ExploreActivity
import com.app.dubaiculture.ui.preLogin.PreLoginActivity
import com.app.dubaiculture.ui.preLogin.login.viewmodels.LoginViewModel
import com.app.dubaiculture.utils.event.Event
import com.app.dubaiculture.utils.killSessionAndStartNewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : BaseBottomSheetFragment<FragmentLogoutBinding>() ,View.OnClickListener{


    private val loginViewModel: LoginViewModel by viewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLogoutBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener(this)
        subscribeToObservable()
    }
    override fun onClick(v: View?) {
        when(v?.id){

            R.id.btn_logout -> {
                loginViewModel.loginStatus.postValue(Event(false))
            }

        }
    }



    private fun subscribeToObservable(){
        loginViewModel.loginStatus.observe(viewLifecycleOwner) { isLoggedIn ->
            isLoggedIn.getContentIfNotHandled()?.let {
                if (!it){
                    loginViewModel.getUserIfExists()
                }

            }

        }
        loginViewModel.userLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                loginViewModel.removeUser(it)
                application.auth.isLoggedIn = false
                activity.killSessionAndStartNewActivity(PreLoginActivity::class.java)
//                loginViewModel.removeUser(it)
            }
        }

    }

}