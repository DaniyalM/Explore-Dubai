package com.app.dubaiculture.ui.postLogin.more.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentLatestNewsBinding
import com.app.dubaiculture.ui.base.BaseFragment

class LatestNewsFragment : BaseFragment<FragmentLatestNewsBinding>(), View.OnClickListener {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLatestNewsBinding.inflate(inflater, container, false)

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}