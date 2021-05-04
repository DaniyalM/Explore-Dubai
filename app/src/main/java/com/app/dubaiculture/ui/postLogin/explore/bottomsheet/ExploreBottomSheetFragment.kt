package com.app.dubaiculture.ui.postLogin.explore.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Gallery
import com.app.dubaiculture.data.repository.exploremap.model.ExploreMap
import com.app.dubaiculture.databinding.FragmentExploreButtomSheetBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.explore.map.adapter.ExploreMapAdapter
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.CATEGORY
import com.app.dubaiculture.utils.Constants.NavBundles.EXPLORE_MAP_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreBottomSheetFragment : BaseBottomSheetFragment<FragmentExploreButtomSheetBinding>() {
    private lateinit var exploreMapList: ArrayList<ExploreMap>


    lateinit var exploreNearAdapter: ExploreMapAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentExploreButtomSheetBinding.inflate(inflater,container,false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.apply {
            exploreMapList = getParcelableArrayList(EXPLORE_MAP_LIST)!!
            binding.headingMuseumsNear.text = "${getString(CATEGORY) + " ${resources.getString(R.string.near_you)}" }"
            rvSetUp(exploreMapList)
        }


    }
    private fun rvSetUp(list: List<ExploreMap>) {
        exploreNearAdapter = ExploreMapAdapter(isArabic())
        binding.rvListing.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = exploreNearAdapter
        }
        exploreNearAdapter.explore = list
        exploreNearAdapter.notifyDataSetChanged()
    }
}