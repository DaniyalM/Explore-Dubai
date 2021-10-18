package com.app.dubaiculture.ui.postLogin.events.detail.registernow.attachmentOptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentAttachmentOptionBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttachmentOptionFragment(val iface :AttachmentOptionClickListener) : BaseBottomSheetFragment<FragmentAttachmentOptionBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.tvDocument.setOnClickListener {
            iface.onOptionSelection(2)
            dismiss()
        }
        binding.tvGallery.setOnClickListener {
            iface.onOptionSelection(1)
            dismiss()
        }

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAttachmentOptionBinding.inflate(inflater,container,false)
    interface AttachmentOptionClickListener {
        fun onOptionSelection(position: Int)
    }
}