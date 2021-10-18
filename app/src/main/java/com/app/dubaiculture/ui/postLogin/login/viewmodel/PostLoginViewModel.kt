package com.app.dubaiculture.ui.postLogin.login.viewmodel

//import com.app.dubaiculture.ui.postLogin.login.PostLoginFragmentDirections
import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.login.LoginRepository
import com.app.dubaiculture.data.repository.login.local.UAEPass
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.app.dubaiculture.data.repository.login.remote.request.UAELoginRequest
import com.app.dubaiculture.data.repository.user.UserRepository
import com.app.dubaiculture.data.repository.user.mapper.transform
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.AuthUtils
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import transformUaeResponse
import javax.inject.Inject

@HiltViewModel
class PostLoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    application: Application,
) : BaseViewModel(application) {
    private val activity = getApplication<ApplicationEntry>()

    var phone: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    var btnEnabled: ObservableBoolean = ObservableBoolean(false)

    val isPhone = MutableLiveData<Boolean?>(true)
    val isEmail = MutableLiveData<Boolean?>(true)

    val isPhoneEdit = MutableLiveData<Boolean?>(true)
    val isEmailEdit = MutableLiveData<Boolean?>(true)

    val isPassword = MutableLiveData<Boolean?>(true)

    //boolean for checking typing email or number
    val isLoginWithPhone = MutableLiveData<Boolean?>(false)

    val mobileNumberError = MutableLiveData<String?>()

    val phoneError = MutableLiveData<Boolean?>(false)

    val emailError = MutableLiveData<Boolean?>(false)


    private val errs_ = MutableLiveData<Int>().apply { value = R.string.err_password }
    val errs: LiveData<Int> = errs_


    private var passwordError_ = MutableLiveData<Int>()
    var passwordError: LiveData<Int> = passwordError_

    private var _loginStatus: MutableLiveData<Event<Boolean>> = MutableLiveData()
    var loginStatus: MutableLiveData<Event<Boolean>> = _loginStatus


    fun loginWithUae(uaeLoginRequest: UAELoginRequest){
        viewModelScope.launch {
            showLoader(true)
            when(val result=loginRepository.loginWithUae(uaeLoginRequest)){
                is Result.Success ->{
                    showLoader(false)
                    val uaePass = transformUaeResponse(result.value.loginResponseDTO.userUaePass)
                    uaePass.let {
                        if (it.idn.isEmpty()) {
                            showAlert(message = activity.resources.getString(R.string.sop1))
                        } else {
                            //setting UaePassInfo for Session
                            userRepository.saveUaeInfo(
                                it
                            )

                            //setting user for Session
                            val user = transform(
                                result.value.loginResponseDTO.userDTO,
                                result.value.loginResponseDTO
                            ).copy(
                                idn = it.idn,
                                userName = "${it.firstNameEn} ${it.lastNameEn}",
                                email = it.email,
                                phoneNumber = "+${it.mobile}"
                            )

                            setUser(user, true)
                            activity.auth.isGuest = false
                            //Saving User Session
                            userRepository.updateUser(user)
                            _loginStatus.value=Event(true)
                        }
                    }

                }
                is Result.Failure -> {
                    showLoader(false)
                    showAlert(message = result.errorMessage?:"Server Error")
                }
            }
        }
    }

    fun loginWithPhone(ph: String? = null, pass: String? = null) {
        viewModelScope.launch {
            showLoader(true)
            LoginRequest(
                phoneNumber = ph.toString().trim(),
                password = pass.toString().trim()
            ).let {
                when (val result = loginRepository.login(it)) {
                    is Result.Success -> {
                        showLoader(false)
                        if (result.value.succeeded) {
                            if (!result.value.isConfirmed) {
                                showErrorDialog(message = result.value.errorMessage)
                                resendPhoneVerification()
                            } else {
                                Timber.e(result.value.loginResponseDTO.userDTO.Email)
                                setUser(
                                    transform(
                                        result.value.loginResponseDTO.userDTO,
                                        result.value.loginResponseDTO
                                    )
                                )
                                userRepository.saveUser(
                                    userDTO = result.value.loginResponseDTO.userDTO,
                                    loginResponseDTO = result.value.loginResponseDTO
                                )
                                _loginStatus.value = Event(true)
                            }
                        } else {
                            showLoader(false)
                            showErrorDialog(
                                message = result.value.errorMessage,
                                colorBg = R.color.red_600
                            )
//                            showToast(message = result.value.errorMessage)

                        }
                    }
                    is Result.Error -> {
                        showLoader(false)
                        showErrorDialog(message = result.exception.toString())
                    }
                    is Result.Failure -> {
                        showLoader(false)
                        showErrorDialog(
                            message = Constants.Error.INTERNET_CONNECTION_ERROR,
                            colorBg = R.color.red_600
                        )
//                        showToast(message = Constants.Error.INTERNET_CONNECTION_ERROR)


                    }
                }
            }
        }
    }


    fun loginWithEmail(eml: String? = null, pass: String? = null) {
        if (!isCheckValid())
            return
        viewModelScope.launch {
            showLoader(true)
            LoginRequest(
                email = eml.toString().trim(),
                password = pass.toString().trim()
            ).let {
                when (val result = loginRepository.loginWithEmail(it)) {
                    is Result.Success -> {
                        if (result.value.succeeded) {
                            showLoader(false)

                            if (!result.value.isConfirmed) {
                                showErrorDialog(message = result.value.errorMessage)
                                resendEmailVerification()
                            } else {
                                Timber.e(result.value.loginResponseDTO.userDTO.Email)
                                setUser(
                                    transform(
                                        result.value.loginResponseDTO.userDTO,
                                        result.value.loginResponseDTO
                                    )
                                )

                                userRepository.saveUser(
                                    userDTO = result.value.loginResponseDTO.userDTO,
                                    loginResponseDTO = result.value.loginResponseDTO
                                )
                                _loginStatus.value = Event(true)
                            }
                        } else {
                            showLoader(false)
                            showErrorDialog(
                                message = result.value.errorMessage,
                                colorBg = R.color.red_600
                            )
//                            showToast(message = result.value.errorMessage)

                        }
                    }
                    is Result.Error -> {
                        showLoader(false)
                        showErrorDialog(message = result.exception.toString())

                    }
                    is Result.Failure -> {
                        showErrorDialog(
                            message = Constants.Error.INTERNET_CONNECTION_ERROR,
                            colorBg = R.color.red_600
                        )
//                        showToast(message = Constants.Error.INTERNET_CONNECTION_ERROR)

                        showLoader(false)

                    }
                }
            }
        }
    }

    private fun resendEmailVerification() {
        viewModelScope.launch {
            showLoader(true)
            LoginRequest(
                email = phone.get().toString().trim()
            ).let {
                when (val result = loginRepository.resendVerification(it)) {
                    is Result.Success -> {
                        if (result.value.succeeded) {
                            showLoader(false)
                            //  showErrorDialog(message = result.message, colorBg = R.color.green_error)
                            Timber.e(result.value.resendVerificationResponseDTO.verificationCode)

//                            navigateByDirections(PostLoginFragmentDirections.actionPostLoginFragmentToPostOTPDialogFragment(
//                                    verificationCode = result.value.resendVerificationResponseDTO.verificationCode,
//                                    emailorphone = phone.get().toString().trim(),
//                                    password = password.get().toString().trim(),
//                                    screenName = Constants.NavBundles.COMES_FROM_LOGIN))

                        } else {
                            showLoader(false)
                            if (result.value.errorMessage.isNullOrEmpty()) {
                                showErrorDialog(
                                    message = result.value.errorMessage,
                                    colorBg = R.color.red_600
                                )
//                                showToast(message = result.value.errorMessage)

                            }
                        }
                    }
                    is Result.Error -> {
//                        showToast(result.exception.toString())

                    }
                    is Result.Failure -> {
                        showErrorDialog(
                            message = Constants.Error.INTERNET_CONNECTION_ERROR,
                            colorBg = R.color.red_600
                        )
//                        showToast(message = Constants.Error.INTERNET_CONNECTION_ERROR)

                    }
                }
            }
            showLoader(false)
        }
    }

    private fun resendPhoneVerification() {
        viewModelScope.launch {
            showLoader(true)
            LoginRequest(
                phoneNumber = phone.get().toString().trim()
            ).let {
                when (val result = loginRepository.resendVerification(it)) {
                    is Result.Success -> {
                        if (result.value.succeeded) {
                            showLoader(false)
//                            showErrorDialog(message = result.message, colorBg = R.color.green_error)
                            Timber.e(result.value.resendVerificationResponseDTO.verificationCode)
//                            navigateByDirections(PostLoginFragmentDirections.actionPostLoginFragmentToPostOTPDialogFragment(
//                                    verificationCode =
//                                    result.value.resendVerificationResponseDTO.verificationCode,
//                                    emailorphone = phone.get().toString().trim(),
//                                    password = password.get().toString().trim(),
//                                    screenName = Constants.NavBundles.COMES_FROM_LOGIN))
                        } else {
                            showErrorDialog(
                                message = result.value.errorMessage,
                                colorBg = R.color.red_600
                            )
//                            showToast(message = result.value.errorMessage)

                        }
                    }
                    is Result.Error -> {
                    }
                    is Result.Failure -> {
                        showErrorDialog(
                            message = Constants.Error.INTERNET_CONNECTION_ERROR,
                            colorBg = R.color.red_600
                        )
//                        showToast(message =Constants.Error.INTERNET_CONNECTION_ERROR)

                    }
                }
            }
            showLoader(false)
        }
    }

    fun onPhoneChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        phone.set(s.toString())
        isLoginWithPhone.value = loginTypeChecker(s)
        val number = AuthUtils.isPhoneNumberValidate(phone.get().toString().trim())
        isPhoneEdit.value = number!!.isValid
        isEmailEdit.value = AuthUtils.isEmailValid(s.toString().trim())
        phoneError.value = !s.contains("[a-zA-Z]".toRegex())
        emailError.value = s.contains("[a-zA-Z]".toRegex())
        errs_.value = AuthUtils.errorsEmailAndPhone(s.toString())
    }

    fun onPasswordChanged(s: CharSequence, start: Int, befor: Int, count: Int) {
        password.set(s.toString())
        if (password.get().toString().trim().isNullOrEmpty()) {
            passwordError_.value = R.string.required
            isPassword.value = false
        } else {
            passwordError_.value = R.string.no_error
            isPassword.value = true
        }
    }

    private fun loginTypeChecker(s: CharSequence): Boolean {
        return if (!s.contains("[a-zA-Z]".toRegex())) {
            val number = AuthUtils.isPhoneNumberValidate(phone.get().toString().trim())
            isPhone.value = number!!.isValid
            Timber.e("Phone")
            true
        } else {
            isEmail.value = AuthUtils.isEmailValid(s.toString().trim())
            Timber.e("Email")
            false
        }
    }

    private fun isCheckValid(): Boolean {
        var isValid = true
        isPhoneEdit.value = AuthUtils.isValidMobileNumber(phone.get().toString().trim())
        isEmailEdit.value = AuthUtils.isEmailValid(phone.get().toString().trim())
        phoneError.value = !phone.get().toString().contains("[a-zA-Z]".toRegex())
        emailError.value = phone.get().toString().contains("[a-zA-Z]".toRegex())
        isPhone.value = !phone.get().toString().isNullOrEmpty()

        if (password.get().toString().trim().isNullOrEmpty()) {
            passwordError_.value = R.string.required
            isPassword.value = false
        } else {
            passwordError_.value = R.string.no_error
            isPassword.value = true
        }
        errs_.value = AuthUtils.errorsEmailAndPhone(phone.get().toString().trim())
        return isValid
    }
}