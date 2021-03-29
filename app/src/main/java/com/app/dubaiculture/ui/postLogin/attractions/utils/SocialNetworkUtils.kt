package com.app.dubaiculture.ui.postLogin.attractions.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri


object SocialNetworkUtils {


    fun openUrl(url: String?, context: Activity) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        context.startActivity(i)
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

