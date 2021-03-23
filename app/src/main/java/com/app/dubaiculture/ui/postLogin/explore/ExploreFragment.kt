package com.app.dubaiculture.ui.postLogin.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.databinding.FragmentExploreBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.explore.adapters.ExploreRecyclerAsyncAdapter
import com.app.dubaiculture.ui.postLogin.explore.viewmodel.ExploreViewModel
import com.app.dubaiculture.utils.handleApiError
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>() {
    @Inject
    lateinit var glide: RequestManager

    private val exploreViewModel: ExploreViewModel by viewModels()
    private lateinit var exploreAdapter: ExploreRecyclerAsyncAdapter
    private lateinit var explore: List<Explore>
//    val handler = Handler(Looper.getMainLooper())

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentExploreBinding.inflate(inflater, container, false)


    fun getRecyclerView() = binding.rvExplore

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
        subscribeUiEvents(exploreViewModel)
        callingObservables()
        subscribeToObservable()


        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            callingObservables()
        }

    }

    private fun setUpRecyclerView() {
        exploreAdapter = ExploreRecyclerAsyncAdapter(activity, fragment = this, application = application)
//        explore.items = createTestItems()
        binding.rvExplore.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(activity)
            adapter = exploreAdapter
            this.itemAnimator = SlideInLeftAnimator()
//            LinearSnapHelper().attachToRecyclerView(this)

        }

    }


    private fun callingObservables() {
        if (!this::explore.isInitialized){
            lifecycleScope.launch {
                exploreViewModel.getExploreToScreen(getCurrentLanguage().language)
            }
        }

    }

    private fun subscribeToObservable() {
        exploreViewModel.exploreList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.let {
                        explore=it.value
                        exploreAdapter.items = explore
                    }
                }
                is Result.Failure -> handleApiError(it, exploreViewModel)
            }
        }
    }


}