package com.app.dubaiculture.ui.postLogin.more.contact.feedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentSharedFeebackBinding
import com.app.dubaiculture.ui.base.BaseFragment

class SharedFeeback : BaseFragment<FragmentSharedFeebackBinding>() {


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSharedFeebackBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}