package com.app.dubaiculture.utils.security.lds

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

@RequiresApi(Build.VERSION_CODES.M)
class AES : SecurityManagerLDS() {

    private val IV_LENGTH: Int = 12
    private val TRANSFORMATION: String = "AES/GCM/NoPadding"

    override fun encrypt(message: String): String {
        val secretKey: SecretKey = getSecretKey()
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val cipherText = cipher.doFinal(message.toByteArray())
        return Base64.encodeToString(cipher.iv + cipherText, Base64.DEFAULT)
    }

    override fun decrypt(cipherString: String): String {
        val cipherText = Base64.decode(cipherString, Base64.DEFAULT)
        return decrypt(cipherText)
    }

    private fun decrypt(cipherText: ByteArray): String {
        val iv = cipherText.copyOfRange(0, IV_LENGTH)
        val cipher = cipherText.copyOfRange(IV_LENGTH, cipherText.size)
        return decrypt(iv, cipher)

    }

    private fun decrypt(encryptionIv: ByteArray, cipherText: ByteArray): String {
        val secretKey: SecretKey = getSecretKey()
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, encryptionIv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        val decodedData = cipher.doFinal(cipherText)
        return String(decodedData) // return decrypted string

    }

    // the secretKey returned is symmetric
    private fun getSecretKey(): SecretKey {
        val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            //Before a keystore can be accessed, it must be loaded
            load(null)
        }
        when (keyStore.containsAlias(ALIAS)) {
            //if secret key does not exist against this alias then create new else return existing secret key
            false -> {
                val keyGenerator: KeyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    PROVIDER
                )

                val keyGenParameterSpec: KeyGenParameterSpec =
                    KeyGenParameterSpec.Builder(
                        ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .build()

                keyGenerator.init(keyGenParameterSpec)
                return keyGenerator.generateKey()
            }

            true -> {

                val secretKeyEntry = keyStore
                    .getEntry(ALIAS, null) as KeyStore.SecretKeyEntry
                return secretKeyEntry.secretKey
            }
        }

    }
}
// AES is a symmetric algorithm which uses the same
// 128, 192, or 256 bit key for both encryption and decryption
// (the security of an AES system increases exponentially with key length)