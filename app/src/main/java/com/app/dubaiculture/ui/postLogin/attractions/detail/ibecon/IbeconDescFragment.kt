package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.sitemap.local.BeaconItems
import com.app.dubaiculture.databinding.FragmentIbeconDescBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.glideInstance
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IbeconDescFragment : BaseFragment<FragmentIbeconDescBinding>(), View.OnClickListener {


    private val beaconArgs: IbeconDescFragmentArgs by navArgs()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentIbeconDescBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beaconArgs.beaconItem.let {
            binding.imgDetailPic.glideInstance(it.image, false).into(binding.imgDetailPic)
            binding.tvTitle.text = it.title
            binding.desc.text = it.summary
            binding.imgBack.setOnClickListener(this)
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_back -> {
                back()
            }
        }
    }
}