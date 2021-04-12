package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentIbeconDescBinding
import com.app.dubaiculture.databinding.FragmentSiteMapBinding
import com.app.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IbeconDescFragment : BaseFragment<FragmentIbeconDescBinding>() , View.OnClickListener{



    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentIbeconDescBinding.inflate(inflater,container,false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onClick(v: View?) {
        when(v?.id){

        }
}
}