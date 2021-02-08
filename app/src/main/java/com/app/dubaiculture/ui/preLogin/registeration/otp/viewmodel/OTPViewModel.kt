package com.app.dubaiculture.ui.preLogin.registeration.otp.viewmodel

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.registeration.RegistrationRepository
import com.app.dubaiculture.data.repository.registeration.remote.request.confirmOTP.ConfirmOTPRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class OTPViewModel @ViewModelInject constructor(private val registrationRepository: RegistrationRepository,application: Application):BaseViewModel(application) {

    // Countdown time
    private val _currentTime = MutableLiveData<Long>()
   private val currentTime: LiveData<Long> = _currentTime

    // The String version of the current time
    val currentTimeString = Transformations.map(currentTime) { time ->
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

    fun confirmOTP(verificationCode: String , otp : String) {
        viewModelScope.launch {
            showLoader(true)
            ConfirmOTPRequest(
                verificationCode = verificationCode,
                otp = otp
            ).let {
                when (val result = registrationRepository.validateOTP(it)) {
                    is Result.Success -> {
                        showToast(result.value.confirmOTPResponseDTO.message.toString())
                    }
                    is Result.Failure -> {
                        Timber.e(result.errorCode.toString())
                        showToast(result.errorCode.toString())
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
}