package com.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.more.local.GetMessage
import com.dubaiculture.data.repository.popular_service.ServiceRepository
import com.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.dialogs.ServiceDownVoteFeedBackFragmentDirections
import com.dubaiculture.utils.AuthUtils.isEmailValid
import com.dubaiculture.utils.Constants.NavBundles.SERVICE_ID
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceDownVoteFeedBackViewModel @Inject constructor(
    application: Application,
    val serviceRepository: ServiceRepository,
    val savedStateHandle: SavedStateHandle
) :
    BaseViewModel(application) {

    var locale: String = ""
    private val _downVote: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val downVote: LiveData<Event<Boolean>> = _downVote

    private val _upVote: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val upVote: LiveData<Event<Boolean>> = _upVote

    val btnSubmitObserver: ObservableBoolean = ObservableBoolean(false)
    var email: ObservableField<String> =
        ObservableField(getApplication<ApplicationEntry>().auth.user?.email)
    var fullName: ObservableField<String> =
        ObservableField("")
    var comment: ObservableField<String> = ObservableField("")

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btnSubmitObserver.set(
            isEmailValid(s.toString().trim()) && fullName.get().toString().trim()
                .isNotEmpty() && comment.get().toString().trim().isNotEmpty()
        )
    }

    fun onFullNameChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btnSubmitObserver.set(
            isEmailValid(email.get().toString().trim()) && s.toString().trim()
                .isNotEmpty() && comment.get().toString().trim().isNotEmpty()
        )
    }

    fun onCommentChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        btnSubmitObserver.set(
            isEmailValid(email.get().toString().trim()) && fullName.get().toString().trim()
                .isNotEmpty() && s.toString().trim().isNotEmpty()
        )
    }

    fun upvoteService(itemId: String) {
        viewModelScope.launch {
            showLoader(true)
            when (val result = serviceRepository.postUpvote(
                EServiceRequest(
                    id = itemId
                )
            )) {
                is Result.Success -> {
                    showLoader(false)
                    if (result.value)
                        showToast(R.string.success)
                }
                is Result.Failure -> {
                    showLoader(false)
                }

            }
        }
    }

    fun postComment() {
        viewModelScope.launch {
            showLoader(true)
            when (val result = serviceRepository.postServiceComment(
                EServiceRequest(
                    fullName = fullName.get().toString(),
                    email = email.get().toString(),
                    comment = comment.get().toString(),
                    id = savedStateHandle.get(SERVICE_ID),
                    culture = getApplication<ApplicationEntry>().auth.locale
                )
            )) {
                is Result.Success -> {
                    showLoader(false)
                    navigateByDirections(
                        ServiceDownVoteFeedBackFragmentDirections.actionServiceDownVoteFeedBackFragmentToMessageDialogFragment(
                            GetMessage(
                                heading = result.value.Result.MessageHeading,
                                message = result.value.Result.MessageBody,
                                reference = result.value.Result.Reference,
                            )
                        )
                    )
//                    showErrorDialog(
//                        title = result.value.Result.MessageHeading,
//                        message = "${result.value.Result.MessageBody} ${result.value.Result.Reference}",
//                    )


                }
                is Result.Failure -> {
                    showLoader(false)
                }

            }
        }


    }
}