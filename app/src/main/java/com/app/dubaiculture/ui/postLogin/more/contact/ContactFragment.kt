package com.app.dubaiculture.ui.postLogin.more.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentContactBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.more.viewmodel.MoreViewModel

class ContactFragment : BaseFragment<FragmentContactBinding>(),View.OnClickListener {
    private val moreViewModel : MoreViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentContactBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(moreViewModel)
        binding.imgClose.setOnClickListener(this)
        binding.tvTitle.text = ""
        binding.tvContactDesc.text = ""
        binding.tvLocationTitle.text = ""




    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close->{
                back()
            }
        }
    }

}