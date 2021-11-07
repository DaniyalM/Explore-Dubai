package com.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.popular_service.ServiceRepository
import com.dubaiculture.data.repository.popular_service.remote.request.EServiceRequest
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.AuthUtils.isEmailValid
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceDownVoteFeedBackViewModel @Inject constructor(
    application: Application,
    val serviceRepository: ServiceRepository
) :
    BaseViewModel(application) {

    private val _downVote: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val downVote: LiveData<Event<Boolean>> = _downVote

    val btnSubmitObserver: ObservableBoolean = ObservableBoolean(false)
    var email: ObservableField<String> = ObservableField("")
    var fullName: ObservableField<String> = ObservableField("")
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

    fun postComment() {
        viewModelScope.launch {
            showLoader(true)
            when (val result = serviceRepository.postServiceComment(
                EServiceRequest(
                    fullName = fullName.get().toString(),
                    email = email.get().toString(),
                    comment = comment.get().toString()
                )
            )) {
                is Result.Success -> {
                    showLoader(false)

                    _downVote.value = Event(result.value)
                }
                is Result.Failure -> {
                    showLoader(false)
                }

            }
        }


    }
}