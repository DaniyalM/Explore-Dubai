package com.app.dubaiculture.ui.postLogin.more.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentLogoutBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutFragment : BaseBottomSheetFragment<FragmentLogoutBinding>() ,View.OnClickListener{



    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLogoutBinding.inflate(inflater,container,false)

    override fun onClick(v: View?) {
        when(v?.id){

        }
    }

}