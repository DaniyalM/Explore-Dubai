package com.dubaiculture.ui.postLogin.messageDialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.dubaiculture.databinding.FragmentMessageDialogBinding
import com.dubaiculture.ui.base.BaseDialogFragment
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.dialogs.ClickBack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageDialogFragment : BaseDialogFragment<FragmentMessageDialogBinding>() {
    private val messageDialogFragmentArgs: MessageDialogFragmentArgs by navArgs()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMessageDialogBinding.inflate(inflater, container, false)


    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setLayout(width, height)
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.messageItem = messageDialogFragmentArgs.message
        binding.done.setOnClickListener {
            bus.post(ClickBack.doBack(true))
            dismiss()

        }
    }

}