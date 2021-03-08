package com.app.dubaiculture.ui.postLogin.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentEventFilterBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventListingFragment : BaseFragment<FragmentEventFilterBinding>() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )=FragmentEventFilterBinding.inflate(inflater,container,false)

}