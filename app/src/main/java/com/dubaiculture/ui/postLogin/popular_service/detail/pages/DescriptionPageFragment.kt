package com.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dubaiculture.data.repository.popular_service.local.models.Description
import com.dubaiculture.databinding.ItemsServiceDetailDescLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.popular_service.detail.ServiceDetailFragment
import com.dubaiculture.ui.postLogin.popular_service.detail.ServiceDetailFragmentDirections
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels.DescriptionViewModel
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.openPdf
import com.dubaiculture.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class DescriptionPageFragment(val description: List<Description>, val category: String? = null) :
    BaseFragment<ItemsServiceDetailDescLayoutBinding>() {

    var readMoreFlag = false
    private val descriptionViewModel: DescriptionViewModel by viewModels()
    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale(getCurrentLanguage().language)
            }
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ItemsServiceDetailDescLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(descriptionViewModel)
        if (description[0].startServiceUrl.isEmpty())
            binding.commonBtn.hide()
        else
            binding.commonBtn.show()


        binding.commonBtn.text = description[0].startServiceText
        binding.commonBtn.setOnClickListener {

            (parentFragment as ServiceDetailFragment).navigateByDirections(
                ServiceDetailFragmentDirections.actionServiceDetailFragmentToWebViewFragment(
                    description[0].startServiceUrl, false, description[0].title
                )
            )
//            (parentFragment as ServiceDetailFragment).navigateByDirections(
//                ServiceDetailFragmentDirections.actionServiceDetailFragment2ToEServiceFragment(
//                    "NOCForm"
//                )
//            )
        }
        bgRTL(binding.imgSpeaker)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (description.isNotEmpty()) {
            subscribeToObservables()
//            val readMoreOption = getReadMoreOptions(
//                    activity,
//                    object : ReadMoreClickListener {
//                        override fun onClick(readMore: Boolean) {
//                            readMoreFlag=readMore
//                        }
//                    })
//
//            if (readMoreFlag)


            val description = description[0]
            binding.imgSpeaker.setOnClickListener {

                if (textToSpeechEngine.isSpeaking) {
                    textToSpeechEngine.stop()
                } else {
                    textToSpeechEngine.speak(
                        "${description.title} ${description.descriptions}",
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "tts1"
                    )
                }
            }
            binding.description = description
            binding.category = category!!
            binding.tvPdfTitle.text = description.fileName
            binding.fileSize.text = description.fileSize
            binding.fileViewLink.setOnClickListener {

//                (parentFragment as ServiceDetailFragment).navigateByDirections(
//                    ServiceDetailFragmentDirections.actionServiceDetailFragmentToWebViewFragment(
//                        BuildConfig.BASE_URL_SHARE +description.documentLink, true
//                    )
//                )
                descriptionViewModel.getDoc(description.documentLink)
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