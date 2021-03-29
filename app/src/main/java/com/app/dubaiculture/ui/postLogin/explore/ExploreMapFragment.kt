package com.app.dubaiculture.ui.postLogin.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentExploreBinding
import com.app.dubaiculture.databinding.FragmentExploreMapBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.explore.adapters.ExploreMapHeaderItems

class ExploreMapFragment : BaseFragment<FragmentExploreMapBinding>() , View.OnClickListener{
    private var adapter = ExploreMapHeaderItems ()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentExploreMapBinding.inflate(inflater,container,false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
    private fun setupRecyclerView() {
        binding.rvMapHeader.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMapHeader.adapter = adapter

        binding.rvExploreMap.layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.rvExploreMap.adapter = adapter
    }
    override fun onClick(v: View?) {
        when(v?.id){

        }
    }
}