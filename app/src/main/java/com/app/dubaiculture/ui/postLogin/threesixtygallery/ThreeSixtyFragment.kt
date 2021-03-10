package com.app.dubaiculture.ui.postLogin.threesixtygallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentThreeSixtyBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.threesixtygallery.adapter.ThreeSixtyItems
import com.app.dubaiculture.ui.postLogin.threesixtygallery.viewmodel.ThreeSixtyViewModel

class ThreeSixtyFragment : BaseFragment<FragmentThreeSixtyBinding>(), View.OnClickListener {
    private val threeSixtyViewModel: ThreeSixtyViewModel by viewModels()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(threeSixtyViewModel)
        initRv()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentThreeSixtyBinding.inflate(inflater, container, false)

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_back -> {
                back()
            }
        }
    }

    private fun initRv() {
        binding.rv360View.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
            groupAdapter.apply {
                add(ThreeSixtyItems(R.drawable.gallery))
                add(ThreeSixtyItems(R.drawable.gallery))
                add(ThreeSixtyItems(R.drawable.gallery))
                add(ThreeSixtyItems(R.drawable.gallery))
                add(ThreeSixtyItems(R.drawable.gallery))
                add(ThreeSixtyItems(R.drawable.gallery))

            }
        }
    }
}