package com.app.dubaiculture.ui.postLogin.attractions.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentAttractionDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_snippet.view.*

@AndroidEntryPoint
class AttractionDetailFragment : BaseFragment<FragmentAttractionDetailBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionDetailBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.root.uniqueToolbar.visibility=View.GONE
        binding.root.detailToolbar.visibility=View.VISIBLE

    }
}