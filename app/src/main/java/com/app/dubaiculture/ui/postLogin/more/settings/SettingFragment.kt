package com.app.dubaiculture.ui.postLogin.more.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentNotificationBinding
import com.app.dubaiculture.databinding.FragmentSettingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(),View.OnClickListener {



    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )=  FragmentSettingBinding.inflate(inflater,container,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noti.setOnClickListener(this)
        binding.reset.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.noti ->{
                navigate(R.id.action_settingFragment_to_notificationSettingFragment)
            }
            R.id.reset->{
            }
        }
    }
}