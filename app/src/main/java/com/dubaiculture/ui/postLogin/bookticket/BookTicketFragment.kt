package com.dubaiculture.ui.postLogin.bookticket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentBookTicketBinding
import com.dubaiculture.databinding.FragmentLoginBinding
import com.dubaiculture.ui.base.BaseFragment

class BookTicketFragment : BaseFragment<FragmentBookTicketBinding>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentBookTicketBinding.inflate(inflater, container, false)
}