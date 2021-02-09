package com.app.dubaiculture.utils

import android.os.Build
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import com.app.dubaiculture.R
import java.util.regex.Matcher
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
    fun isValidMobile(ph: String): Boolean {
        return Pattern.matches("^(92)\\d{10}\$", ph)
    }


    fun isValidMobileTest(ph: String): String {
//        return Pattern.matches("^(92)\\d{10}\$", ph)
            val isvalid = true
        if(!ph.matches("^(92)\\d{0}\$".toRegex())){
            return "Start with 92"

        }else if(!ph.matches("^(92)\\d{10}\$".toRegex())){
            return "Invalid Mobile Number"
        }
        return ""
    }

    fun isValidMobile1(ph: String): Int {

        return if (!(Pattern.matches("05\\d{8}", ph)
                    || Pattern.matches("^0.1{8}", ph)))
            R.string.mobile_number
        else R.string.valid

    }

    fun isMatchPassword(password : String ,confirmPass :String):Boolean{
        return password == confirmPass

    }

    fun isValidPasswordFormat(pass: String): Boolean {

        if(pass.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$".toRegex()))
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
//    fun isValidPasswordFormat(password: String): Boolean {
//        val passwordREGEX = Pattern.compile("^[A-z]" +
//                "(?=.*[0-9])" +         //at least 1 digit
//                "(?=.*[a-z])" +         //at least 1 lower case letter
//                "(?=.*[A-Z])" +         //at least 1 upper case letter
////                "(?=.*[a-zA-Z])" +      //any letter
//                "(?=.*[@#$%^&+=])" +    //at least 1 special character
//                "(?=\\S+$)" +           //no white spaces
////                ".{2,}" +               //at least 8 characters
//                "$")
//        return passwordREGEX.matcher(password).matches()
//    }




}

//var isValid = false
//if (pass.matches("(?=.*[A-Z])".toRegex())) {
//    isValid = true
//}else {
//    isValid = false
//}
//if (pass.matches(".*[!,@,#,\$,%,^,&,*,?,_,~,-,(,)].*".toRegex())) {
//    isValid = true
//}else {
//    isValid = false
//}
//if (pass.matches(".*[a-zA-Z].*".toRegex())) {
//    isValid = true
//}else {
//    isValid = false
//}
//if (pass.matches(".*\\d+.*".toRegex())) {
//    isValid = true
//}else {
//    isValid = false
//}
//isValid = pass.matches(".{8,}\$".toRegex())
//return isValid