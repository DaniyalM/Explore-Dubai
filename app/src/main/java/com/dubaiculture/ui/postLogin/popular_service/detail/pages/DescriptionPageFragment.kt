package com.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dubaiculture.R
import com.dubaiculture.data.repository.popular_service.local.models.Description
import com.dubaiculture.databinding.ItemsServiceDetailDescLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.popular_service.detail.ServiceDetailFragment
import com.dubaiculture.ui.postLogin.popular_service.detail.ServiceDetailFragmentDirections
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels.DescriptionViewModel
import com.dubaiculture.utils.Constants.NavBundles.CATEGORY
import com.dubaiculture.utils.Constants.NavBundles.DESCRIPTION_LIST
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.openPdf
import com.dubaiculture.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class DescriptionPageFragment :
    BaseFragment<ItemsServiceDetailDescLayoutBinding>() {
    private var description: List<Description>? = null
    private var category: String? = null

    companion object {
        fun newInstance(
            description: List<Description>,
            category: String? = null
        ): DescriptionPageFragment {
            val args = Bundle()
            args.putParcelableArrayList(DESCRIPTION_LIST, ArrayList(description))
            args.putString(CATEGORY, category)
            val fragment = DescriptionPageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var readMoreFlag = false
    private val descriptionViewModel: DescriptionViewModel by viewModels()
    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale(getCurrentLanguage().language)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            description = it.getParcelableArrayList(DESCRIPTION_LIST)
            category = it.getString(CATEGORY)
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ItemsServiceDetailDescLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(descriptionViewModel)
        if (description?.get(0)?.startServiceUrl?.isEmpty() == true)
            binding.commonBtn.hide()
        else
            binding.commonBtn.show()


        binding.commonBtn.text = description?.get(0)?.startServiceText
        binding.commonBtn.setOnClickListener {

//            (parentFragment as ServiceDetailFragment).navigateByDirections(
//                ServiceDetailFragmentDirections.actionServiceDetailFragmentToWebViewFragment(
//                    description?.get(0)?.startServiceUrl?:"", false, description?.get(0)?.title?:""
//                )
//            )
//
//            openWebWithoutBaseUrl(description?.get(0)?.startServiceUrl?:"")
//            (parentFragment as ServiceDetailFragment).navigateByDirections(
//                ServiceDetailFragmentDirections.actionServiceDetailFragmentToWebViewFragment(
//                    description?.get(0)?.startServiceUrl ?: "",
//                    false,
//                    description?.get(0)?.title ?: ""
//                )
//            )
//            (parentFragment as ServiceDetailFragment).navigateByDirections(
//                ServiceDetailFragmentDirections.actionServiceDetailFragmentToWebViewFragment(
//                    description[0].startServiceUrl, false
//                )
//            )
            if (application.auth.isGuest) {
                (parentFragment as ServiceDetailFragment).navigateByDirections(
                    ServiceDetailFragmentDirections.actionServiceDetailFragment2ToPostLoginBottomNavigation()
                )
            } else {
                (parentFragment as ServiceDetailFragment).navigateByDirections(
                    ServiceDetailFragmentDirections.actionServiceDetailFragment2ToEServiceFragment(
                        description?.get(0)?.title ?: "",
                        description?.get(0)?.formName ?: "",
                        description?.get(0)?.formSubmitURL ?: ""
                    )
                )
            }

        }
        bgRTL(binding.imgSpeaker)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (description?.isNotEmpty() == true) {
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


            val description = description!![0]
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
            if (description.documentLink.isEmpty())
                binding.tvPdfTitle.text = resources.getString(R.string.not_available)
            else {
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