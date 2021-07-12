package com.app.dubaiculture.utils

import com.app.dubaiculture.R
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class AuthUtilsTest{
    @Test
    fun `empty username return false`(){
        val result = AuthUtils.fullNameIsNotEmpty("")
        assertThat(result).isFalse()
    }

    @Test
    fun `check email is empty  and check email is valid or not`(){
        val emptyEmail = AuthUtils.isEmailErrors("")
        assertThat(emptyEmail).isEqualTo(R.string.required)
        val wrongEmail = AuthUtils.isEmailErrors("abc@gmai")
        assertThat(wrongEmail).isEqualTo(R.string.err_email)
        val correctEmail = AuthUtils.isEmailErrors("abc@gmail.com")
        assertThat(correctEmail).isEqualTo(R.string.no_error)
    }

    @Test
    fun `empty email return false and check wrong email pattern and check correct email pattern`(){
        val result = AuthUtils.isEmailErrorsbool("")
        assertThat(result).isFalse()
        val checkWrongEmailPattern = AuthUtils.isEmailErrorsbool("abc@gmail00")
        assertThat(checkWrongEmailPattern).isFalse()
        val checkCorrectEmailPattern = AuthUtils.isEmailErrorsbool("abc@gmail.com")
        assertThat(checkCorrectEmailPattern).isTrue()
    }
    @Test
    fun `empty phone number return false`(){
        val emptyPhone  = AuthUtils.errorsPhone("")
        assertThat(emptyPhone).isEqualTo(R.string.required)
    }
    @Test
    fun `phone num should be started with +`(){
        val emptyPhone  = AuthUtils.errorsPhone("12")
        assertThat(emptyPhone).isEqualTo(R.string.mobile_number_)
    }
    @Test
    fun `phone num is not a valid mobile number`(){
        val phoneCode = AuthUtils.isPhoneNumberValidate("+23357598479")
        assertThat(phoneCode?.isValid).isFalse()
    }
    @Test
    fun `check num is a valid national number with country code`(){
        val phoneCode = AuthUtils.isPhoneNumberValidate("+923357598479")
        assertThat(phoneCode?.isValid).isTrue()
    }
    @Test
    fun `check password is empty return required`(){
        val firstLetter  = AuthUtils.passwordErrors("")
        assertThat(firstLetter).isEqualTo(R.string.required)
    }

    @Test
    fun `check password it should be at least 8 characters`(){
        val firstLetter  = AuthUtils.passwordErrors("Pak@1")
        assertThat(firstLetter).isEqualTo(R.string.should_contain_8_character_long)
    }

    @Test
    fun `check password it should be at least one Upper Case is not written`(){
        val upperCase  = AuthUtils.passwordErrors("pak@1234")
        assertThat(upperCase).isEqualTo(R.string.should_contain_a_upper_character)
    }
    @Test
    fun `check password error , at least one lower case letter is not written`(){
        val lowerCase  = AuthUtils.passwordErrors("PAK@12345")
        assertThat(lowerCase).isEqualTo(R.string.lowercase_character)
    }
    @Test
    fun `check password error , at least one digit is not written`(){
        val oneDigit  = AuthUtils.passwordErrors("PAKistan@")
        assertThat(oneDigit).isEqualTo(R.string.at_least_one_digit)
    }

    @Test
    fun `check password error , at least one special character is not written`(){
        val specialLetter  = AuthUtils.passwordErrors("PAKistan123")
        assertThat(specialLetter).isEqualTo(R.string.should_contain_a_special_character)
    }
    @Test
    fun `confirm password is not match return false`(){
        val confirmPassAndPass  = AuthUtils.isMatchPassword("Pakistan@123","Pakistan@12")
        assertThat(confirmPassAndPass).isFalse()
    }

    @Test
    fun `confirm password and password are match`(){
        val match  = AuthUtils.isMatchPassword("Pakistan@123","Pakistan@123")
        assertThat(match).isTrue()
    }
}