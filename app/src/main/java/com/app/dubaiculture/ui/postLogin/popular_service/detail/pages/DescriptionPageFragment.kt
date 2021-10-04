package com.app.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.data.repository.popular_service.local.models.Description
import com.app.dubaiculture.databinding.ItemsServiceDetailDescLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels.DescriptionViewModel
import com.app.dubaiculture.utils.openPdf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@AndroidEntryPoint
class DescriptionPageFragment(val description: List<Description>, val category: String? = null) :
    BaseFragment<ItemsServiceDetailDescLayoutBinding>() {
    private val descriptionViewModel: DescriptionViewModel by viewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ItemsServiceDetailDescLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(descriptionViewModel)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (description.isNotEmpty()) {
            subscribeToObservables()
            val description = description[0]
            binding.description = description
            binding.category = category!!
            binding.fileViewLink.setOnClickListener {
                descriptionViewModel.getDoc("http://www.africau.edu/images/default/sample.pdf")

//                if (description.documentLink.isNotEmpty()){
//                }
            }
        }
    }

    private fun subscribeToObservables() {
        descriptionViewModel.docBody.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {

                lifecycleScope.launch {
                    activity.openPdf(descriptionViewModel.saveFile(it))
                }

            }
        }
    }





}