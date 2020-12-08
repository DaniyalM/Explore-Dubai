package com.daniyal.dubalculture.utils.security.lds

import android.content.Context
import android.security.KeyPairGeneratorSpec
import android.util.Base64
import java.math.BigInteger
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.util.*
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal


class RSA constructor(context: Context) : SecurityManagerLDS() {

    private val TRANSFORMATION: String = "RSA/ECB/PKCS1Padding"
    private var keyPair: KeyPair

    init {
        val keyStore: KeyStore = KeyStore.getInstance(PROVIDER).apply {
            //Before a keystore can be accessed, it must be loaded
            load(null)
        }
        keyPair = if (!keyStore.containsAlias(ALIAS)) {
            val start: Calendar = Calendar.getInstance()
            val end: Calendar = Calendar.getInstance()
            end.add(Calendar.YEAR, 50)
            val spec = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                KeyPairGeneratorSpec.Builder(context)
                    .setAlias(ALIAS)
                    .setSubject(X500Principal("CN=$ALIAS"))
                    .setSerialNumber(BigInteger.ONE)
                    .setStartDate(start.getTime())
                    .setEndDate(end.getTime())
                    .build()
            } else {
                TODO("VERSION.SDK_INT < JELLY_BEAN_MR2")
            }
            val kpg = KeyPairGenerator.getInstance("RSA", PROVIDER)
            kpg.initialize(spec)
            kpg.generateKeyPair()
        } else {
            val privateKey = keyStore.getKey(ALIAS, null) as PrivateKey?
            val publicKey = keyStore.getCertificate(ALIAS)?.publicKey
            KeyPair(publicKey, privateKey)
        }
    }

    override fun encrypt(message: String): String {
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.public)
        val bytes = cipher.doFinal(message.toByteArray())
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    override fun decrypt(cipherString: String): String {
        val encryptedData = Base64.decode(cipherString, Base64.DEFAULT)
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, keyPair.private)
        val decodedData = cipher.doFinal(encryptedData)
        return String(decodedData)
    }

}