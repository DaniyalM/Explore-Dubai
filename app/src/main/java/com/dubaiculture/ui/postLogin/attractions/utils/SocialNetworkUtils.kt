package com.dubaiculture.ui.postLogin.attractions.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.dubaiculture.BuildConfig


object SocialNetworkUtils {
    fun openUrl(
        url: String, context: Activity,
        isFacebook:Boolean=false,
        isTwitter:Boolean=false,
        isLinkedIn:Boolean=false,
        isInstagram:Boolean=false,
        isYoutube:Boolean=false,
        isWeb:Boolean=false,

    ) {

        Intent(Intent.ACTION_VIEW).apply {
            var URL = url
            if (URL == "null" || URL.isEmpty()) {
                if (isFacebook){ URL="https://www.facebook.com/DubaiCulture/" }
                if (isTwitter){ URL="https://twitter.com/DubaiCulture" }
                if (isInstagram){ URL="https://www.instagram.com/dubaiculture/" }
                if (isLinkedIn){ URL="https://www.linkedin.com/company/dubai-culture-&-arts-authority/" }
                if (isYoutube){ URL="https://www.youtube.com/user/DubaiCulture" }
                if (isWeb){
                    URL=url
                }
            }
            data = Uri.parse(URL)
            context.startActivity(this)
        }

    }

    fun getFacebookPage(faceBookUrl: String, context: Activity) {
        val packageManager: PackageManager = context.packageManager
        try {
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                openUrl("fb://facewebmodal/f?href=${faceBookUrl}", context)
            } else {
                openUrl("fb://page/", context)
            }
        } catch (ex: PackageManager.NameNotFoundException) {
            openUrl("fb://facewebmodal/f?href=${faceBookUrl}", context)
        }
    }


    fun instagramNavigationIntent(pm: PackageManager, url: String = ""): Intent? {
        var url = url
        val intent = Intent(Intent.ACTION_VIEW)
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length - 1)
                }
//                val username = url.substring(url.lastIndexOf("/") + 1)
                val username = "CMuhzIRsyTb/"
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
//                intent.data = Uri.parse("http://instagram.com/_u/$username")
                intent.data = Uri.parse("http://instagram.com/p/$username")
                intent.setPackage("com.instagram.android")
                return intent
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        intent.data = Uri.parse(url)
        return intent
    }

    fun twitterNavigationIntent(pm: PackageManager, url: String = ""): Intent? {
        var url = url
        val intent = Intent(Intent.ACTION_VIEW)
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length - 1)
                }
//                val username = url.substring(url.lastIndexOf("/") + 1)
                val username = "CMuhzIRsyTb/"
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
//                intent.data = Uri.parse("http://instagram.com/_u/$username")
                intent.data = Uri.parse("http://instagram.com/p/$username")
                intent.setPackage("com.instagram.android")
                return intent
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        intent.data = Uri.parse(url)
        return intent
    }

    fun linkedInNavigationIntent(pm: PackageManager, url: String = ""): Intent? {
        var url = url
        val intent = Intent(Intent.ACTION_VIEW)
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length - 1)
                }
//                val username = url.substring(url.lastIndexOf("/") + 1)
                val username = "CMuhzIRsyTb/"
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
//                intent.data = Uri.parse("http://instagram.com/_u/$username")
                intent.data = Uri.parse("http://instagram.com/p/$username")
                intent.setPackage("com.instagram.android")
                return intent
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        intent.data = Uri.parse(url)
        return intent
    }

    fun facebookNavigationIntent(pm: PackageManager, url: String = ""): Intent? {
        var url = url
        val intent = Intent(Intent.ACTION_VIEW)
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length - 1)
                }
//                val username = url.substring(url.lastIndexOf("/") + 1)
                val username = "CMuhzIRsyTb/"
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
//                intent.data = Uri.parse("http://instagram.com/_u/$username")
                intent.data = Uri.parse("http://instagram.com/p/$username")
                intent.setPackage("com.instagram.android")
                return intent
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        intent.data = Uri.parse(url)
        return intent
    }

    fun youtubeNavigationIntent(pm: PackageManager, url: String = ""): Intent? {
        var url = url
        val intent = Intent(Intent.ACTION_VIEW)
        try {
            if (pm.getPackageInfo("com.instagram.android", 0) != null) {
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length - 1)
                }
//                val username = url.substring(url.lastIndexOf("/") + 1)
                val username = "CMuhzIRsyTb/"
                // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
//                intent.data = Uri.parse("http://instagram.com/_u/$username")
                intent.data = Uri.parse("http://instagram.com/p/$username")
                intent.setPackage("com.instagram.android")
                return intent
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        intent.data = Uri.parse(url)
        return intent
    }
}

