package com.app.dubaiculture.utils

import android.os.Build
import android.text.TextUtils
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import com.app.dubaiculture.R
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import timber.log.Timber
import java.util.*
import java.util.regex.Pattern


object AuthUtils {
    fun isEmailValid(email: String): Boolean {
        val regExpn = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

        val pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)

        return matcher.matches()
    }
    fun isValidEmiratesId(id: String): Int {
        return if (!Pattern.matches("784-.\\d{3}-.\\d{6}-\\d{1}", id))
            R.string.emirates_id
        else R.string.valid
    }




    fun isValidMobileNumber(phone: String?): Boolean {
        if (TextUtils.isEmpty(phone)) return false
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        try {

            val numberProto: PhoneNumber = phoneNumberUtil.parse(phone, "")
            val countryCode = numberProto.countryCode

            val phoneNumber = phoneNumberUtil.parse(phone, Locale.getDefault().country)
            Timber.e("Country Code =>$countryCode")
            val phoneNumberType = phoneNumberUtil.getNumberType(phoneNumber)
            return phoneNumberType == PhoneNumberType.MOBILE
            } catch (e: java.lang.Exception) {
        }
        return false
    }

    fun isMatchPassword(password: String, confirmPass: String):Boolean{
        return password == confirmPass
    }
    fun isMatchPasswordError(password: String, confirmPass: String):Int{
        if(confirmPass.isBlank()){
            return R.string.required
        }else
        if(password!=confirmPass){
            return R.string.err_confirm
        }
        return R.string.no_error
    }
    fun isMatchPasswordBool(password: String, confirmPass: String):Boolean{
        if(confirmPass.isBlank() || password.isNullOrEmpty()){
            return false
        }else
            if(password!=confirmPass){
                return false
            }
        return true
    }
    fun isValidPasswordFormat(pass: String): Boolean {
        if(pass.matches("^(?=.*\\d)(?=.*[<>/?!@#\$%^,*()&+=~.])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$".toRegex()))
      return true
        return false
    }

    fun isValidOTP(otp: String): Boolean {
        val matches = Pattern.matches("\\d{6}", otp)
        return matches
    }

    fun hideStatusBar(window: Window){
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try{
                window.insetsController?.hide(WindowInsets.Type.statusBars())
            }catch (e: Exception){
                print(e.stackTrace) }
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

    }
    fun errorsEmailAndPhone(s: String):Int{

            if(checkLoginType(s)){
            //Login With Phone
            return if(s.isNullOrBlank()){
                R.string.required
            }else
            if(!s.startsWith("+")){
                R.string.mobile_number_
            }else if(!isValidMobileNumber(s)){
                R.string.err_phone
            }else{
                R.string.no_error
            }
        }else{
            //Login With Email
            return if(!(isEmailValid(s))){
                R.string.err_email
            }else{
                R.string.err_phone_two
            }
        }
        }
    fun checkLoginType(s: String):Boolean{
        return !s.contains("[a-zA-Z]".toRegex())

    }
    fun passwordErrors(s: String):Int{
        return if(s.isEmpty()){
            R.string.required
        }else if(s.length< 8){
            R.string.should_contain_8_character_long
        }else if(!s.contains("(?=.*[A-Z])".toRegex())){
            R.string.should_contain_a_upper_character
        }else if(!s.contains("(?=.*[a-z])".toRegex())){
            R.string.lowercase_character
        }else if(!s.contains("(.*\\d.*)".toRegex())){
            R.string.at_least_one_digit
        }else if(!s.contains("(?=.*[<>/?!@#\$%^,*()&+=~.])".toRegex())){
            R.string.should_contain_a_special_character
        }else{
            R.string.err_password
        }
    }
fun isEmailErrors(s: String): Int{
    return when {
        s.isBlank() || s.isNullOrEmpty()-> {
            R.string.required
        }
        !isEmailValid(s) -> {
            R.string.err_email
        }
        else -> {
            R.string.err_phone_two
        }
    }
}
    fun isEmailErrorsbool(s: String): Boolean{
        return if(s.trim().isBlank()){
            false
        }else isEmailValid(s)
    }
    fun errorsPhone(s: String):Int{
            //Login With Phone
            return if(s.isBlank()||s.isNullOrBlank()){
                R.string.required
            }else
                if(!s.startsWith("+")){
                    R.string.mobile_number_
                }else if(!isValidMobileNumber(s)){
                    R.string.err_phone
                }else{
                    R.string.required
                }
        }
    fun isPhoneNumberValidate(mobNumber: String?): PhoneValidateResponse? {
        val phoneNumberValidate = PhoneValidateResponse()
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        var phoneNumber: PhoneNumber? = null
        var finalNumber = false
        var isMobile: PhoneNumberType? = null
        var isValid = false
        try {
            val numberProto: PhoneNumber = phoneNumberUtil.parse(mobNumber, "")
            val countryCode = numberProto.countryCode
            val isoCode = phoneNumberUtil.getRegionCodeForCountryCode(countryCode)
            phoneNumber = phoneNumberUtil.parse(mobNumber, isoCode)
            isValid = phoneNumberUtil.isValidNumber(phoneNumber)
            isMobile = phoneNumberUtil.getNumberType(phoneNumber)
            phoneNumberValidate.code = phoneNumber.countryCode.toString()
            phoneNumberValidate.phone = phoneNumber.nationalNumber.toString()
            phoneNumberValidate.isValid = false
        } catch (e: NumberParseException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        if (isValid && (PhoneNumberType.MOBILE == isMobile ||
                    PhoneNumberType.FIXED_LINE_OR_MOBILE == isMobile || PhoneNumberType.FIXED_LINE_OR_MOBILE ==)
        ) {
            finalNumber = true
            phoneNumberValidate.isValid = true
        }
        return phoneNumberValidate
    }


}



