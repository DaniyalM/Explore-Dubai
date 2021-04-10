package com.app.dubaiculture.ui.postLogin.attractions.detail.ar.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentARDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment

class ARDetailFragment : BaseFragment<FragmentARDetailBinding>(), View.OnClickListener {



    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentARDetailBinding.inflate(inflater,container,false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
          binding.tvDetail.text =  it.getString("desc")
        }
        binding.imgClose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close->{
                back()
            }
        }
    }
}