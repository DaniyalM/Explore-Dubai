package com.app.dubaiculture.ui.postLogin.explore

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
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
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>() {
    @Inject
    lateinit var glide: RequestManager

    private val exploreViewModel: ExploreViewModel by viewModels()
    private lateinit var exploreAdapter: ExploreRecyclerAsyncAdapter
    private lateinit var explore: List<Explore>
    private var lastFirstVisiblePosition: Int = 0
    private var mustSeelastFirstVisiblePosition: Int = 0
    private var eventslastFirstVisiblePosition: Int = 0
    private var popularServicelastFirstVisiblePosition: Int = 0
    private var newslastFirstVisiblePosition: Int = 0
//    val handler = Handler(Looper.getMainLooper())

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentExploreBinding.inflate(inflater, container, false)


    fun getRecyclerView() = binding.rvExplore

    override fun onPause() {
        super.onPause()
        lastFirstVisiblePosition =
            (getRecyclerView().layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!this::exploreAdapter.isInitialized) {
            binding.swipeRefresh.post(object : Runnable {
                override fun run() {
                    binding.swipeRefresh.isRefreshing = true
                    callingObservables()

                }

            })
            setUpRecyclerView()
        }
        subscribeUiEvents(exploreViewModel)
//        callingObservables()
        subscribeToObservable()


        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            exploreViewModel.getExploreToScreen(getCurrentLanguage().language)

        }

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark)





        binding.root.img_drawer.setOnClickListener {
            navigate(R.id.action_exploreFragment_to_exploreMapFragment)
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    showAlert(
                        message = getString(R.string.error_msg),
                        textPositive = getString(R.string.okay),
                        textNegative = getString(R.string.cancel),
                        actionNegative = {

                        },
                        actionPositive = {
                            activity.finish()
                        }
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

    private fun setUpRecyclerView() {
        exploreAdapter = ExploreRecyclerAsyncAdapter(activity,
            fragment = this,
            baseViewModel = exploreViewModel)
//        explore.items = createTestItems()
        binding.rvExplore.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(activity)
            adapter = exploreAdapter
            this.itemAnimator = SlideInLeftAnimator()
            (layoutManager as LinearLayoutManager).scrollToPosition(lastFirstVisiblePosition)

//            LinearSnapHelper().attachToRecyclerView(this)

        }

    }


    private fun callingObservables() {
        if (!this::explore.isInitialized) {

            lifecycleScope.launch {
                exploreViewModel.getExploreToScreen(getCurrentLanguage().language)
            }
        }

    }

    private fun subscribeToObservable() {

        exploreViewModel.isFavourite.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            when (it) {
                is Result.Success -> {

                    if (TextUtils.equals(it.value.Result.message, "Added")) {
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_fav)
                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_home)
                    }
                }
                is Result.Failure -> handleApiError(it, exploreViewModel)
            }
        }
        exploreViewModel.exploreList.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            when (it) {
                is Result.Success -> {

                    it.let {
                        exploreAdapter.items=it.value
                    }
                }
                is Result.Failure -> handleApiError(it, exploreViewModel)
            }
        }
    }


}