package com.dubaiculture.ui.postLogin.bookticket.termandcondition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentTermConditionBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment

class TermConditionFragment : BaseBottomSheetFragment<FragmentTermConditionBinding>() ,View.OnClickListener{


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )=FragmentTermConditionBinding.inflate(inflater,container,false)

    override fun onClick(v: View?) {

    }


}