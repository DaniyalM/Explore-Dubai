package com.app.dubaiculture.ui.postLogin.events

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.request.EventRequest
import com.app.dubaiculture.data.repository.filter.models.SelectedItems
import com.app.dubaiculture.databinding.FragmentEventFilterBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.components.EventHeaderItemSelector
import com.app.dubaiculture.ui.postLogin.events.filter.viewmodel.FilterViewModel
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.app.dubaiculture.utils.AppConfigUtils.clickCheckerFlag
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.dateFormat
import com.app.dubaiculture.utils.getDateObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.event_search_toolbar.view.*
import kotlinx.coroutines.launch
import q.rorbin.badgeview.QBadgeView
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EventFilterFragment : BaseFragment<FragmentEventFilterBinding>(), View.OnClickListener {
    private val eventViewModel: EventViewModel by activityViewModels()

    private var isContentLoaded = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.root.img_filter.setOnClickListener(this)
        binding.root.back.setOnClickListener(this)
        subscribeUiEvents(eventViewModel)
//        callingObservables()
//        subscribeToObservables()
        initiatePager()
        viewPagerSetUp()




        eventViewModel.filterDataList.observe(viewLifecycleOwner){
            if(!it.isNullOrEmpty()){
                // badge should be visible
                binding.showTabHeader.visibility = View.GONE
                badgeSetUp(it)
            }else{
                // badge should be gone
                badgeSetUp(it)
                binding.showTabHeader.visibility = View.VISIBLE
            }
        }
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
            R.id.img_filter -> {
                navigate(R.id.action_eventFilterFragment_to_filterFragment)
            }
            R.id.back -> {
                back()
            }
        }
    }
private fun badgeSetUp(list:List<SelectedItems>){
    QBadgeView(requireContext())
        .setBadgeBackgroundColor(R.color.purple_900)
        .bindTarget(binding.root.badge_placement).setBadgeNumber(list.size)
//        .stroke(R.color.black_900, 6F, true)\
        .setBadgeGravity(Gravity.START or Gravity.TOP)
        .setGravityOffset(18F, 6F, true)
//        .badgeTextColor = Color.parseColor("#FFFFFF")

}


}