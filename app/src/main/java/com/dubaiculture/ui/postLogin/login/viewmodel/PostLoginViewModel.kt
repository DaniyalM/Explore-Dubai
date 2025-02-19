package com.dubaiculture.ui.postLogin.login.viewmodel

//import com.dubaiculture.ui.postLogin.login.PostLoginFragmentDirections
import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.login.LoginRepository
import com.dubaiculture.data.repository.login.local.UAEPass
import com.dubaiculture.data.repository.login.remote.request.LoginRequest
import com.dubaiculture.data.repository.login.remote.request.UAELoginRequest
import com.dubaiculture.data.repository.user.UserRepository
import com.dubaiculture.data.repository.user.local.User
import com.dubaiculture.data.repository.user.mapper.transform
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.ui.postLogin.login.PostLoginFragmentDirections
import com.dubaiculture.utils.AuthUtils
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.dataStore.DataKeys
import com.dubaiculture.utils.dataStore.DataStoreManager
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import transformUaeResponse
import javax.inject.Inject

@HiltViewModel
class PostLoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    private val dateStoreManager: DataStoreManager,
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


    private val _isSheetOpen: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isSheetOpen: MutableLiveData<Event<Boolean>> = _isSheetOpen

    val _user: MutableLiveData<Event<User>> = MutableLiveData()
    val user: MutableLiveData<Event<User>> = _user
    val _userUae: MutableLiveData<Event<UAEPass>> = MutableLiveData()
    val userUae: MutableLiveData<Event<UAEPass>> = _userUae
    fun updateSheet(flag: Boolean) {
        _isSheetOpen.value = Event(flag)
    }

    //    fun loginWithUae(uaeLoginRequest: UAELoginRequest, linkAccount: Boolean = false) {
//        viewModelScope.launch {
//            showLoader(true)
//            if (linkAccount) {
//                when (val result = loginRepository.linkWithUae(uaeLoginRequest)) {
//                    is Result.Success -> {
//                        showLoader(false)
//                        //UAE Response Has been Saved
//
//                        val uaePass =
//                            transformUaeResponse(result.value.loginResponseDTO.userUaePass)
//                        uaePass.let {
//                            userRepository.saveUaeInfo(
//                                it
//                            )
//
//                            //setting user for Session
//                            val user = transform(
//                                result.value.loginResponseDTO.userDTO,
//                                result.value.loginResponseDTO
//                            ).copy(
//                                idn = it.idn,
//                                userName = "${it.firstNameEn} ${it.lastNameEn}",
//                                email = it.email,
//                                phoneNumber = "+${it.mobile}"
//                            )
//
//                            setUser(user)
//                            //Saving User Session
//                            userRepository.updateUser(user)
//                        }
//
//
//                    }
//                    is Result.Failure -> {
//                        showLoader(false)
//                        val error = result.errorMessage ?: Constants.Error.SOMETHING_WENT_WRONG
//                        showAlert(message = error)
//                    }
//                }
//            } else {
//                when (val result = loginRepository.loginWithUae(uaeLoginRequest)) {
//                    is Result.Success -> {
//                        showLoader(false)
//                        //UAE Response Has been Saved
//
//                        if (!result.value.loginResponseDTO.IsLinked) {
//                            updateSheet(true)
//                        } else {
//                            val uaePass =
//                                transformUaeResponse(result.value.loginResponseDTO.userUaePass)
//                            uaePass.let {
//                                userRepository.saveUaeInfo(
//                                    it
//                                )
//
//                                //setting user for Session
//                                val user = transform(
//                                    result.value.loginResponseDTO.userDTO,
//                                    result.value.loginResponseDTO
//                                ).copy(
//                                    idn = it.idn,
//                                    userName = "${it.firstNameEn} ${it.lastNameEn}",
//                                    email = it.email,
//                                    phoneNumber = "+${it.mobile}"
//                                )
//
//                                setUser(user)
//                                //Saving User Session
//                                userRepository.updateUser(user)
//                            }
//                        }
//
//
//                    }
//                    is Result.Failure -> {
//                        showLoader(false)
//                        val error = result.errorMessage ?: Constants.Error.SOMETHING_WENT_WRONG
//                        showAlert(message = error)
//                    }
//                }
//
//            }
//        }
//    }
    fun createAccount(user: User, uaePass: UAEPass,message:String="") {
        uaePass.let {
            viewModelScope.launch {
                userRepository.saveUaeInfo(
                    it
                )

                //setting user for Session
                if (message.isNotEmpty()) {
                    showSnackbar(message = message)
                    Handler(Looper.getMainLooper()).postDelayed({
                        setUser(user)
                    }, 1500)

                } else {
                    setUser(user)
                }

                activity.auth.isGuest = false
                //Saving User Session
                userRepository.updateUser(user)
                _loginStatus.value = Event(true)


            }
        }
    }

//    fun loginWithUaeCreate(uaeLoginRequest: UAELoginRequest) {
//        viewModelScope.launch {
//            showLoader(true)
//            when (val result = loginRepository.linkWithUaeCreateAccount(uaeLoginRequest)) {
//                is Result.Success -> {
//                    showLoader(false)
////                    Timber.e(result.value.loginResponseDTO.userDTO.Email)
//
//                    //UAE Response Has been Saved
//                    val uaePass = transformUaeResponse(result.value.loginResponseDTO.userUaePass)
//                    uaePass.let {
//                        if (it.idn.isEmpty()) {
//                            showAlert(message = activity.resources.getString(R.string.sop1))
//                        } else {
//                            //setting UaePassInfo for Session
//                            userRepository.saveUaeInfo(
//                                it
//                            )
//
//                            //setting user for Session
//                            val user = transform(
//                                result.value.loginResponseDTO.userDTO,
//                                result.value.loginResponseDTO
//                            ).copy(
//                                idn = it.idn,
//                                userName = "${it.firstNameEn} ${it.lastNameEn}",
//                                email = it.email,
//                                phoneNumber = "+${it.mobile}"
//                            )
//
//                            setUser(user)
//                            activity.auth.isGuest = false
//                            //Saving User Session
//                            userRepository.updateUser(user)
//                            _loginStatus.value = Event(true)
//                        }
//                    }
//
//
//                }
//                is Result.Failure -> {
//                    showLoader(false)
//                    val error = result.errorMessage ?: Constants.Error.SOMETHING_WENT_WRONG
//                    showAlert(message = error)
//                }
//            }
//        }
//    }

    fun loginWithUae(uaeLoginRequest: UAELoginRequest, linkAccount: Boolean = false) {
        viewModelScope.launch {
            showLoader(true)
            if (linkAccount) {
                when (val result = loginRepository.linkWithUae(uaeLoginRequest)) {
                    is Result.Success -> {
                        showLoader(false)
                        //UAE Response Has been Saved

                        val uaePass =
                            transformUaeResponse(result.value.loginResponseDTO.userUaePass)
                        //setting user for Session
                        val user = transform(
                            result.value.loginResponseDTO.userDTO,
                            result.value.loginResponseDTO
                        ).copy(
                            idn = uaePass.idn,
                            userName = "${uaePass.firstNameEn} ${uaePass.lastNameEn}",
                            email = uaePass.email,
                            phoneNumber = "+${uaePass.mobile}"
                        )

                        createAccount(user,uaePass)
//                        uaePass.let {
//                            if (it.idn.isEmpty()) {
//                                showAlert(message = activity.resources.getString(R.string.sop1))
//                            } else {
//                                //setting UaePassInfo for Session
//                                userRepository.saveUaeInfo(
//                                    it
//                                )
//
//                                //setting user for Session
//                                val user = transform(
//                                    result.value.loginResponseDTO.userDTO,
//                                    result.value.loginResponseDTO
//                                ).copy(
//                                    idn = it.idn,
//                                    userName = "${it.firstNameEn} ${it.lastNameEn}",
//                                    email = it.email,
//                                    phoneNumber = "+${it.mobile}"
//                                )
//
//                                setUser(user)
//                                activity.auth.isGuest = false
//                                //Saving User Session
//                                userRepository.updateUser(user)
//                                _loginStatus.value = Event(true)
//                            }
//                        }


                    }
                    is Result.Failure -> {
                        showLoader(false)
                        val error = result.errorMessage ?: Constants.Error.SOMETHING_WENT_WRONG
                        showAlert(message = error)
                    }
                }
            } else {
                when (val result = loginRepository.loginWithUae(uaeLoginRequest)) {
                    is Result.Success -> {
                        val uaePass =
                            transformUaeResponse(result.value.loginResponseDTO.userUaePass)
                        val user = transform(
                            result.value.loginResponseDTO.userDTO,
                            result.value.loginResponseDTO
                        ).copy(
                            idn = uaePass.idn,
                            userName = "${uaePass.firstNameEn} ${uaePass.lastNameEn}",
                            email = uaePass.email,
                            phoneNumber = "+${uaePass.mobile}"
                        )
                        showLoader(false)


                        if (!result.value.loginResponseDTO.IsLinked) {

                            if (dateStoreManager.getString(DataKeys.USER_SESSION).isNullOrEmpty()){
                                updateSheet(true)
                                _user.value= Event(user)
                                _userUae.value=Event(uaePass)
                            }else {
                                dateStoreManager.setData(DataKeys.USER_SESSION,user.userId)
                                createAccount(user,uaePass)
                            }
                        } else {
                            val message = result.value.loginResponseDTO.UpdateMessage?:""
                            if (message.isNotEmpty()) {
                                showSnackbar(message = message)
                            }
                            createAccount(user,uaePass,message=result.value.loginResponseDTO.UpdateMessage ?: "")
//                            uaePass.let {
//                                if (it.idn.isEmpty()) {
//                                    showAlert(message = activity.resources.getString(R.string.sop1))
//                                } else {
//                                    //setting UaePassInfo for Session
//                                    userRepository.saveUaeInfo(
//                                        it
//                                    )
//
//                                    //setting user for Session
//
//                                    val message = result.value.loginResponseDTO.UpdateMessage ?: ""
//                                    if (message.isNotEmpty()) {
//                                        showSnackbar(message = message)
//                                        Handler(Looper.getMainLooper()).postDelayed({
//                                            setUser(user)
//                                        }, 1500)
//
//                                    } else {
//                                        setUser(user)
//                                    }
//
//                                    activity.auth.isGuest = false
//                                    //Saving User Session
//                                    userRepository.updateUser(user)
//                                    _loginStatus.value = Event(true)
//                                }
//                            }
                        }


                    }
                    is Result.Failure -> {
                        showLoader(false)
                        showAlert(message = result.errorMessage ?: "Server Error")
                    }
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
//                            Timber.e(result.value.resendVerificationResponseDTO.verificationCode)

                            navigateByDirections(
                                PostLoginFragmentDirections.actionPostLoginFragmentToPostOTPDialogFragment(
                                    result.value.resendVerificationResponseDTO.verificationCode,
                                    Constants.NavBundles.COMES_FROM_LOGIN,
                                    phone.get().toString().trim(),
                                    password.get().toString().trim()))


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
//                            Timber.e(result.value.resendVerificationResponseDTO.verificationCode)
                            navigateByDirections(
                                PostLoginFragmentDirections.actionPostLoginFragmentToPostOTPDialogFragment(
                                    result.value.resendVerificationResponseDTO.verificationCode,
                                    Constants.NavBundles.COMES_FROM_LOGIN,
                                    phone.get().toString().trim(),
                                    password.get().toString().trim()))
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