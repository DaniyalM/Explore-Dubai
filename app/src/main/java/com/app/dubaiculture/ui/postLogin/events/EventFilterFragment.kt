package com.app.dubaiculture.ui.postLogin.events

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.activityViewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.filter.models.SelectedItems
import com.app.dubaiculture.databinding.EventSearchToolbarBinding
import com.app.dubaiculture.databinding.FragmentEventFilterBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.app.dubaiculture.utils.AppConfigUtils.clickCheckerFlag
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.AndroidEntryPoint
import q.rorbin.badgeview.QBadgeView
import java.util.*


@AndroidEntryPoint
class EventFilterFragment : BaseFragment<FragmentEventFilterBinding>(), View.OnClickListener {
    private val eventViewModel: EventViewModel by activityViewModels()

    private var isContentLoaded = false
    lateinit var eventSearchToolbarBinding: EventSearchToolbarBinding

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.eventSearchToolbar.imgFilter.setOnClickListener(this)
        binding.eventSearchToolbar.back.setOnClickListener(this)
        binding.eventSearchToolbar.imgSearch.setOnClickListener(this)
        backArrowRTL(binding.eventSearchToolbar.back)

        subscribeUiEvents(eventViewModel)
//        callingObservables()
//        subscribeToObservables()
        initiatePager()
        viewPagerSetUp()




        eventViewModel.filterDataList.observe(viewLifecycleOwner){
            if(!it.isNullOrEmpty()){
                // badge should be visible
                eventSearchToolbarBinding.tvBadge.text = it.size.toString()
                eventSearchToolbarBinding.tvBadge.visibility = View.VISIBLE
                binding.showTabHeader.visibility = View.GONE
            }else{
                // badge should be gone
                eventSearchToolbarBinding.tvBadge.visibility = View.GONE
                binding.showTabHeader.visibility = View.VISIBLE
            }
        }

        eventSearchToolbarBinding.editSearchEvents.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                eventViewModel._searchBarKeyWord.value = Event(eventSearchToolbarBinding.editSearchEvents.text.toString())
                return@OnEditorActionListener true
            }
            false
        })




    }

    private fun initiatePager() {
        binding.pager.isUserInputEnabled = false
//        binding.horizontalSelector.initialize(createItems(),
//            binding.pager,
//            this)
    }


    private fun viewPagerSetUp(){
        isContentLoaded = false
        binding.pager.isSaveEnabled = false
        binding.horizontalSelector.initialize(createItems(),
            binding.pager,
            this)
    }

    override fun onResume() {
        super.onResume()
        binding.horizontalSelector.positionUpdate(clickCheckerFlag)
    }

    private fun createItems(): List<HeaderModel> =
        mutableListOf<HeaderModel>().apply {
            repeat((1..4).count()) {
                when (it) {
                    0 -> {
                        add(
                            HeaderModel(
                                id = it,
                                title = getString(R.string.all),
                            ))
                    }
                    1 -> {
                        add(
                            HeaderModel(
                                id = it,
                                title = getString(R.string.this_week),
                            ))

                    }
                    2 -> {
                        add(
                            HeaderModel(
                                id = it,
                                title = getString(R.string.this_weekend),
                            ))

                    }
                    3 -> {
                        add(
                            HeaderModel(
                                id = it,
                                title = getString(R.string.next_seven_days),
                            ))
                    }
                }
            }
            isContentLoaded = true
        }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventFilterBinding.inflate(inflater, container, false)

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_search ->{
                eventViewModel._searchBarKeyWord.value = Event(eventSearchToolbarBinding.editSearchEvents.text.toString())
            }
            R.id.img_filter -> {
                navigate(R.id.action_eventFilterFragment_to_filterFragment)
            }
            R.id.back -> {
                back()
            }
        }
    }
private fun badgeSetUp(list: List<SelectedItems>){
    QBadgeView(requireContext())
        .setBadgeBackgroundColor(R.color.purple_900)
        .bindTarget(eventSearchToolbarBinding.badgePlacement).setBadgeNumber(list.size)
//        .stroke(R.color.black_900, 6F, true)\
        .setBadgeGravity(Gravity.START or Gravity.TOP)
        .setGravityOffset(18F, 6F, true)
//        .badgeTextColor = Color.parseColor("#FFFFFF")

}


}