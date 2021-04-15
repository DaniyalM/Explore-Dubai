package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

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
import kotlinx.android.synthetic.main.fragment_ibecon.view.*
import kotlinx.android.synthetic.main.fragment_ibecon_desc.view.*

@AndroidEntryPoint
class IbeconDescFragment : BaseDialogFragment<FragmentIbeconDescBinding>() , View.OnClickListener{


    private lateinit var beconObj: IbeconITemsSiteMap

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentIbeconDescBinding.inflate(inflater,container,false)
    override fun getTheme() = R.style.FullScreenDialog;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)

    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog?.window!!.apply {
                setLayout(width, height)
                @Suppress("DEPRECATION")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insetsController?.hide(WindowInsets.Type.statusBars())
                } else {
                    setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )
                }

            }

        }
    }


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