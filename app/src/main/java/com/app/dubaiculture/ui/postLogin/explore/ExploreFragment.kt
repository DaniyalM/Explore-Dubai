package com.app.dubaiculture.ui.postLogin.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.data.repository.explore.local.models.*
import com.app.dubaiculture.databinding.FragmentExploreBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.explore.adapters.ExploreRecyclerAsyncAdapter
import com.app.dubaiculture.ui.postLogin.explore.viewmodel.ExploreViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.exp

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>() {
    @Inject
    lateinit var glide: RequestManager

    private val exploreViewModel: ExploreViewModel by viewModels()
    private lateinit var explore: ExploreRecyclerAsyncAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentExploreBinding.inflate(inflater, container, false)


    fun getRecyclerView() = binding.rvExplore

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        explore = ExploreRecyclerAsyncAdapter(activity)
        subscribeToObservable()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {

//        explore.items = createTestItems()
        binding.rvExplore.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(activity)
            adapter = explore
            explore.provideGlideInstance(glide)

        }

    }

    private fun subscribeToObservable() {
        exploreViewModel.getExploreToScreen()
        exploreViewModel.exploreList.observe(viewLifecycleOwner) {

           it.let { explore.items=it }
        }

    }


}