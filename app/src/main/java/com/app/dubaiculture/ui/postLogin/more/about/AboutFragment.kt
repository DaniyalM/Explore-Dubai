package com.app.dubaiculture.ui.postLogin.more.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentAboutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAboutBinding.inflate(inflater,container,false)

}