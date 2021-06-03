package com.app.dubaiculture.ui.postLogin.more.settings

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.settings.local.UserSettings
import com.app.dubaiculture.databinding.FragmentNotificationBinding
import com.app.dubaiculture.databinding.FragmentSettingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.more.profile.viewmodels.ProfileViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.SETTINGS_BUNDLE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(),View.OnClickListener {
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var userSettings: UserSettings



    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )=  FragmentSettingBinding.inflate(inflater,container,false)




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!this::userSettings.isInitialized){
            profileViewModel.getSettings()
            subscribeToObservable()
            binding.noti.setOnClickListener(this)
            binding.reset.setOnClickListener(this)
        }

    }
    private fun subscribeToObservable(){
        profileViewModel.userSettings.observe(viewLifecycleOwner){
            it?.getContentIfNotHandled()?.let {
                userSettings=it
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.noti ->{

                navigate(R.id.action_settingFragment_to_notificationSettingFragment,Bundle().apply {
                    this.putParcelable(SETTINGS_BUNDLE,userSettings as Parcelable)
                })
            }
            R.id.reset->{
            }
        }
    }
}