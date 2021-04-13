package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.sitemap.local.IbeconITemsSiteMap
import com.app.dubaiculture.databinding.FragmentIbeconDescBinding
import com.app.dubaiculture.databinding.FragmentSiteMapBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.glideInstance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_ibecon.view.*
import kotlinx.android.synthetic.main.fragment_ibecon_desc.view.*

@AndroidEntryPoint
class IbeconDescFragment : BaseFragment<FragmentIbeconDescBinding>() , View.OnClickListener{


    private lateinit var beconObj: IbeconITemsSiteMap

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentIbeconDescBinding.inflate(inflater,container,false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.apply {
            beconObj = getParcelable(Constants.NavBundles.BECON_OBJECT)!!

        }
        binding.imgDetailPic.glideInstance(beconObj.image, false).into(binding.imgDetailPic)
        binding.tvTitle.text = beconObj.title
        binding.desc.text = beconObj.summary
        binding.imgBack.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_back->{
                back()
            }
        }
}
}