package com.app.dubaiculture.ui.postLogin.attractions.detail.login.viewmodel

import android.app.Application
import android.os.Bundle
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.core.os.bundleOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.registeration.RegistrationRepository
import com.app.dubaiculture.data.repository.registeration.remote.request.confirmOTP.ConfirmOTPRequest
import com.app.dubaiculture.data.repository.registeration.remote.request.resendOTP.ResendOTPRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.Error.INTERNET_CONNECTION_ERROR
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class PostOTPViewModel @ViewModelInject constructor(private val registrationRepository: RegistrationRepository, application: Application):
    BaseViewModel(application) {

    // Countdown time
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long> = _currentTime

    // this observer when true then handle to navigate from otpFragment to Login.
    private var _loginConfirmOTPStatus: MutableLiveData<Event<Boolean>> = MutableLiveData()
    var loginConfirmOTPStatus: MutableLiveData<Event<Boolean>> = _loginConfirmOTPStatus

    // The String version of the current time , counter display the time wired in arabic.
    val currentTimeString = Transformations.map(currentTime){ time ->
        DateUtils.formatElapsedTime(time)
    }
    // Countdown time
    companion object {

        // Time when OTP expired
        private const val DONE = 0L
        // Countdown time interval
        private const val ONE_SECOND = 1000L
        // Total time for the OTP
        private const val COUNTDOWN_TIME = 60000L //60000L    1min == 60000 milli seconds
    }
    private val timer: CountDownTimer
    init {
        // Creates a timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ ONE_SECOND
            }
            override fun onFinish() {
                _currentTime.value = DONE
            }
        }
        timer.start()
    }

    fun confirmOTP(verificationCode: String , otp : String?="") {
        viewModelScope.launch {
            showLoader(true)
            ConfirmOTPRequest(
                verificationCode = verificationCode,
                otp = otp?:""
            ).let {
                when (val result = registrationRepository.validateOTP(it)) {
                    is Result.Success -> {
                        if(result.value.succeeded){
                            showLoader(false)
                            showToast(result.value.confirmOTPResponseDTO.message.toString())
                            // registrationSuccess
                            val bundle = Bundle()
                            bundle.putString("key","postFragment")
                            navigateByAction(R.id.action_postOTPDialogFragment_to_registrationSuccessFragment2,bundle)
                        }else {
//                            showToast(result.value.errorMessage)
                            showErrorDialog(message = result.value.errorMessage, colorBg = R.color.red_600)
                        }
                    }
                    is Result.Failure -> {
                        showLoader(false)
                        Timber.e(result.errorCode.toString())
//                        showToast(result.errorCode.toString())
                        showErrorDialog(message = INTERNET_CONNECTION_ERROR, colorBg = R.color.red_600)

                    }
                    is Result.Error -> {
                        Timber.e(result.exception)
                        showToast(result.exception.toString())
                    }
                }
            }
            showLoader(false)
        }
    }


    fun confirmLoginOTP(verificationCode: String , otp : String?="") {
        viewModelScope.launch {
            showLoader(true)
            ConfirmOTPRequest(
                verificationCode = verificationCode,
                otp = otp?:""
            ).let {
                when (val result = registrationRepository.validateOTP(it)) {
                    is Result.Success -> {
                        if(result.value.succeeded){
                            showLoader(false)
                            _loginConfirmOTPStatus.value = Event(true)
                            showToast(result.value.confirmOTPResponseDTO.message.toString())
                        }else {
                            showErrorDialog(message = result.value.errorMessage, colorBg = R.color.red_600)
                        }
                    }
                    is Result.Failure -> {
                        Timber.e(result.errorCode.toString())
                        showErrorDialog(message = INTERNET_CONNECTION_ERROR, colorBg = R.color.red_600)

                    }
                    is Result.Error -> {
                        Timber.e(result.exception)
                        showToast(result.exception.toString())
                    }
                }
            }
            showLoader(false)
        }
    }


    //ValidateOTP for forgot password ..... validateOTP and ConfirmOTP has no difference . just change in endpoint dont know why he creates a new service for forgot
    fun validateOTP(verificationCode: String , otp : String?="") {
        viewModelScope.launch {
            showLoader(true)
            ConfirmOTPRequest(
                verificationCode = verificationCode,
                otp = otp?:""
            ).let {
                when (val result = registrationRepository.validateOTPForgot(it)) {
                    is Result.Success -> {
                        if(result.value.succeeded){
                            showLoader(false)
                            showToast(result.value.confirmOTPResponseDTO.message.toString())
                            val bundle = Bundle()
                            bundle.putString("verificationCode",it.verificationCode)
                            navigateByAction(R.id.action_postOTPDialogFragment_to_postCreatePassFragment,bundle)
                        }else{
                            showErrorDialog(message = result.value.errorMessage, colorBg = R.color.red_600)
                        }
                    }
                    is Result.Failure -> {
                        Timber.e(result.errorCode.toString())
                        showErrorDialog(message = INTERNET_CONNECTION_ERROR, colorBg = R.color.red_600)

                    }
                    is Result.Error -> {
                        Timber.e(result.exception)
                        showToast(result.exception.toString())
                    }
                }
            }
            showLoader(false)
        }
    }
    override fun onCleared() {
        super.onCleared()
        // Cancel the timer
        timer.cancel()
    }


    fun resendOTP(verificationCode: String){
        viewModelScope.launch {
            showLoader(true)
            ResendOTPRequest(
                verificationCode = verificationCode,
            ).let{
                when (val result = registrationRepository.resendVerificationOTP(it)) {
                    is Result.Success -> {
                        if(result.value.succeeded) {
                            timer.start()
                            showToast(result.value.confirmOTPResponseDTO.message.toString())
                        }else{
                            showErrorDialog(message = result.value.errorMessage, colorBg = R.color.red_600)
                        }
                    }
                    is Result.Failure -> {
                        Timber.e(result.errorCode.toString())
                        showErrorDialog(message = INTERNET_CONNECTION_ERROR, colorBg = R.color.red_600)
                    }
                    is Result.Error -> {
                        Timber.e(result.exception)
                        showToast(result.exception.toString())
                    }
                }
            }
            showLoader(false)
        }
    }

//fun isLangArabic(lang:Boolean){
//    if(lang == true){
//                "00:"+currentTime
//    }else{
//        val currentTimeString = Transformations.map(currentTime){ time ->
//            DateUtils.formatElapsedTime(time)
//        }
//    }
//}

}