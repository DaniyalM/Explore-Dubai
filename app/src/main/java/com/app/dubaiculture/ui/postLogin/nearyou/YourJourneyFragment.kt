package com.app.dubaiculture.ui.postLogin.nearyou

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentNearYouBinding
import com.app.dubaiculture.databinding.FragmentYourJourneyBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.base.BaseFragment

class YourJourneyFragment : BaseBottomSheetFragment<FragmentYourJourneyBinding>() {



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentYourJourneyBinding.inflate(inflater,container,false)
}