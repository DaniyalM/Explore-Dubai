package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.sitemap.local.IbeconITemsSiteMap
import com.app.dubaiculture.databinding.FragmentIbeconDescBinding
import com.app.dubaiculture.databinding.FragmentSiteMapBinding
import com.app.dubaiculture.ui.base.BaseDialogFragment
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.glideInstance
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IbeconDescFragment : BaseFragment<FragmentIbeconDescBinding>() , View.OnClickListener{


    private lateinit var beconObj: IbeconITemsSiteMap

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentIbeconDescBinding.inflate(inflater,container,false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            beconObj = getParcelable(Constants.NavBundles.BECON_OBJECT)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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