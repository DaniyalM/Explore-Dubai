package com.daniyal.dubalculture.utils.security.rds

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurityManagerRDS @Inject constructor() {
    //AES only supports key sizes of 16, 24 or 32 bytes
    private val KEY: String = "ABC1234567890ABC"
    private val ALGORITHM: String = "AES"
    private val TRANSFORMATION: String = "AES/CBC/PKCS5Padding"
    private val BYTE_IV = KEY.toByteArray()
    private val CHAR_SET = charset("UTF-8")

    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val key = SecretKeySpec(KEY.toByteArray(CHAR_SET), ALGORITHM)

        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(BYTE_IV))
        val bytes = cipher.doFinal(plainText.toByteArray(CHAR_SET))
        return Base64.getEncoder().encodeToString(bytes)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(cipherText: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val key = SecretKeySpec(KEY.toByteArray(CHAR_SET), ALGORITHM)

        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(BYTE_IV))

        val bytes = Base64.getDecoder().decode(cipherText)
        return String(cipher.doFinal(bytes))
    }
}
// Initialization Vector must be random
// Key must be from keystore
// https://www.cryptofails.com/post/70059609995/crypto-noobs-1-initialization-vectors
// https://crypto.stackexchange.com/questions/28979/whats-the-point-of-an-iv-if-public
// https://stackoverflow.com/questions/22126412/how-to-obtain-different-cipher-text-for-same-plain-text-using-aes/22128005