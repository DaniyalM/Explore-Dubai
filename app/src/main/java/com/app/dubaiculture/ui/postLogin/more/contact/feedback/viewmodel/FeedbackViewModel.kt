package com.app.dubaiculture.ui.postLogin.more.contact.feedback.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.Result.Success
import com.app.dubaiculture.data.repository.more.MoreRepository
import com.app.dubaiculture.data.repository.more.local.FeedbacksType
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequest
import com.app.dubaiculture.data.repository.more.remote.request.ShareFeedbackRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.AuthUtils
import com.app.dubaiculture.utils.Constants.Error.INTERNET_CONNECTION_ERROR
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.launch

class FeedbackViewModel @ViewModelInject constructor(
    application: Application,
    val moreRepository: MoreRepository
) : BaseViewModel(application) {
    private val _typeList: MutableLiveData<Event<List<FeedbacksType>>> = MutableLiveData()
    val typeList: LiveData<Event<List<FeedbacksType>>> = _typeList
    var selectedTypeId: String? = null


    // editext get() and set()
    var fullName: ObservableField<String> = ObservableField("")
    var email: ObservableField<String> = ObservableField("")
    var message: ObservableField<String> = ObservableField("")
    var subject: ObservableField<String> = ObservableField("")
    var type: ObservableField<String> = ObservableField("")


    // booleans for checking regex validation
    val isMessage = MutableLiveData<Boolean?>(true)
    val isEmail = MutableLiveData<Boolean?>(true)
    val isfullName = MutableLiveData<Boolean?>(true)
    val isSubject = MutableLiveData<Boolean?>(true)
    val isType = MutableLiveData<Boolean?>(true)

    private val context = getApplication<ApplicationEntry>()

    // errors

    private var _fullnameError = MutableLiveData<Int>()
    var fullnameError: LiveData<Int> = _fullnameError

    private var _emailError = MutableLiveData<Int>()
    var emailError: LiveData<Int> = _emailError

    private var _messaeError = MutableLiveData<Int>()
    var messaeError: LiveData<Int> = _messaeError

    private var _subjectError = MutableLiveData<Int>()
    var subjectError: LiveData<Int> = _subjectError

    private var _typeError = MutableLiveData<Int>()
    var typeError: LiveData<Int> = _typeError
    fun sharedFeedBackType(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                moreRepository.getFeedbackType(privacyAndTermRequest = PrivacyAndTermRequest(locale))) {
                is Success -> {
                    showLoader(false)
                    _typeList.value = result.value
                }
                is Result.Failure -> {
                    showLoader(false)
                    showErrorDialog(message = INTERNET_CONNECTION_ERROR)
                }
            }
        }
    }

    fun onEmailChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        email.set(s.toString())
        isEmail.value = AuthUtils.isEmailErrorsbool(s.toString().trim())
        _emailError.value = AuthUtils.isEmailErrors(s.toString().trim())
    }

    fun onSubjectChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        subject.set(s.toString())
        if (subject.get().toString().trim().isNullOrEmpty()) {
            isSubject.value = false
            _subjectError.value = R.string.required
        }

        if (!fullName.get().toString().trim().isNullOrEmpty()) {
            isSubject.value = true
            _subjectError.value = R.string.no_error
        }
    }

    fun onMessageChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        message.set(s.toString())
        if (message.get().toString().trim().isNullOrEmpty()) {
            isMessage.value = false
            _messaeError.value = R.string.required
        }
        if (!message.get().toString().trim().isNullOrEmpty()) {
            isMessage.value = true
            _messaeError.value = R.string.no_error
        }
    }


    fun onFullNameChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        fullName.set(s.toString())
        if (fullName.get().toString().trim().isNullOrEmpty()) {
            isfullName.value = false
            _fullnameError.value = R.string.required
        }

        if (!fullName.get().toString().trim().isNullOrEmpty()) {
            isfullName.value = true
            _fullnameError.value = R.string.no_error
        }
    }


    fun onTypeChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        type.set(s.toString())
        if (type.get().toString().trim().isNullOrEmpty()) {
            isType.value = false
            _typeError.value = R.string.required
        }
        if (!type.get().toString().trim().isNullOrEmpty()) {
            isType.value = true
            _typeError.value = R.string.no_error
        }
    }

//    fun setSelectedType(typeID: Int?) {
//        showToast(typeID.toString())
//        selectedTypeId = typeID
//    }

    fun sharedFeedbackPost() {
        if (!isCheckValid())
            return
        viewModelScope.launch {
            showLoader(true)
            ShareFeedbackRequest(
                culture = context.auth.locale.toString(),
                email = email.get().toString().trim(),
                type = selectedTypeId.toString(),
                fullName = fullName.get().toString().trim(),
                message = message.get().toString().trim()
            ).let {
                when (val result = moreRepository.postFeedBack(it)) {
                    is Result.Success -> {

                        showLoader(false)
                        showToast(message =  result.value.message)
                        navigateByBack()


                    }
                    is Result.Failure -> {
                        showErrorDialog(message = result.errorMessage.toString(), colorBg = R.color.red_600)
                        showLoader(false)
                    }
                }
            }
        }
    }

    // validation
    private fun isCheckValid(): Boolean {
        var isValid = true
        _emailError.value = AuthUtils.isEmailErrors(s = email.get().toString().trim())
        isEmail.value = AuthUtils.isEmailErrorsbool(email.get().toString().trim())


        if (fullName.get().toString().trim().isNullOrEmpty()) {
            isfullName.value = false
            _fullnameError.value = R.string.required
            isValid = false
        }
        if (!fullName.get().toString().trim().isNullOrEmpty()) {
            isfullName.value = true
            _fullnameError.value = R.string.no_error

        }
        if (!AuthUtils.isEmailErrorsbool(email.get().toString().trim())) {
            isValid = false
        }


        if (subject.get().toString().trim().isNullOrEmpty()) {
            isSubject.value = false
            _subjectError.value = R.string.required
            isValid = false
        }
        if (!subject.get().toString().trim().isNullOrEmpty()) {
            isSubject.value = true
            _subjectError.value = R.string.no_error

        }


        if (message.get().toString().trim().isNullOrEmpty()) {
            isMessage.value = false
            _messaeError.value = R.string.required
            isValid = false
        }
        if (!message.get().toString().trim().isNullOrEmpty()) {
            isMessage.value = true
            _messaeError.value = R.string.no_error

        }


        if (type.get().toString().trim().isNullOrEmpty()) {
            isType.value = false
            _typeError.value = R.string.required
            isValid = false
        }
        if (!type.get().toString().trim().isNullOrEmpty()) {
            isType.value = true
            _typeError.value = R.string.no_error

        }
        return isValid
    }

}