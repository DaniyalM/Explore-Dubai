package com.dubaiculture.ui.postLogin.attractions.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.fragment.app.Fragment
import com.dubaiculture.ui.postLogin.attractions.detail.AttractionDetailFragment
import com.dubaiculture.ui.postLogin.attractions.detail.AttractionDetailFragmentDirections
import com.dubaiculture.ui.postLogin.events.detail.EventDetailFragment
import com.dubaiculture.ui.postLogin.events.detail.EventDetailFragmentDirections
import com.dubaiculture.ui.postLogin.more.contact.ContactFragment
import com.dubaiculture.ui.postLogin.more.contact.ContactFragmentDirections


object SocialNetworkUtils {
    fun openUrl(
        url: String, context: Activity,
        isFacebook: Boolean = false,
        isTwitter: Boolean = false,
        isLinkedIn: Boolean = false,
        isInstagram: Boolean = false,
        isYoutube: Boolean = false,
        isWeb: Boolean = false,
        fragment: Fragment? = null,
        title: String? = ""
    ) {
        var URL = url
        Intent(Intent.ACTION_VIEW).apply {

            if (URL == "null" || URL.isEmpty()) {

                if (isInstagram) {
                    URL = "https://www.instagram.com/dubaiculture/"
                }
                if (isLinkedIn) {
                    URL = "https://www.linkedin.com/company/dubai-culture-&-arts-authority/"
                }
                if (isWeb) {
                    URL = url
                }
                if ((isFacebook || isTwitter || isYoutube) && resolveActivity(context.packageManager) != null) {
                    if (isFacebook) {
                        URL = "https://www.facebook.com/DubaiCulture/"
                    }
                    if (isTwitter) {
                        URL = "https://twitter.com/DubaiCulture"
                    }
                    if (isYoutube) {
                        URL = "https://www.youtube.com/user/DubaiCulture"
                    }
                    data = Uri.parse(URL)
                    context.startActivity(this)
                } else {
                    when (fragment) {
                        is AttractionDetailFragment -> {
                            fragment.navigateByDirections(
                                AttractionDetailFragmentDirections.actionAttractionDetailFragmentToWebViewNavigation(
                                    URL, false, title
                                )
                            )
                        }
                        is ContactFragment -> {
                            fragment.navigateByDirections(
                                ContactFragmentDirections.actionContactFragmentToWebviewFragment(
                                    URL, false, title
                                )
                            )
                        }
                        is EventDetailFragment -> {
                            fragment.navigateByDirections(
                                EventDetailFragmentDirections.actionEventDetailFragment2ToWebViewNavigation(
                                    URL, false, title
                                )
                            )
                        }
                    }

                }
            } else {
                data = Uri.parse(URL)
                context.startActivity(this)
            }


        }

    }
//    fun getFacebookPage(faceBookUrl: String, context: Activity): Intent? {
//        val packageManager: PackageManager = context.packageManager
//        var uri: Uri = Uri.parse(faceBookUrl)
//        try {
//            val applicationInfo = packageManager.getApplicationInfo("com.facebook.katana", 0)
//            if (applicationInfo.enabled) {
//                // http://stackoverflow.com/a/24547437/1048340
//                uri = Uri.parse("fb://facewebmodal/f?href=$faceBookUrl")
//            }
//        } catch (ignored: PackageManager.NameNotFoundException) {
//        }
//        return Intent(Intent.ACTION_VIEW, uri)
//    }


    fun getFacebookPage(faceBookUrl: String, context: Activity) {
        val packageManager: PackageManager = context.packageManager
        try {
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode

            if (versionCode >= 3002850) {
                openUrl("fb://facewebmodal/f?href=${faceBookUrl}", context)
            } else {
                openUrl("fb://page/DubaiCulture", context)
            }
        } catch (ex: PackageManager.NameNotFoundException) {
            openUrl("", isFacebook = true, context = context)
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
                val username = url.substring(url.lastIndexOf("/") + 1)
//                val username = "CMuhzIRsyTb/"
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
                val username = url.substring(url.lastIndexOf("/") + 1)
//                val username = "CMuhzIRsyTb/"
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
                val username = url.substring(url.lastIndexOf("/") + 1)
//                val username = "CMuhzIRsyTb/"
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
                val username = url.substring(url.lastIndexOf("/") + 1)
//                val username = "CMuhzIRsyTb/"
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
                val username = url.substring(url.lastIndexOf("/") + 1)
//                val username = "CMuhzIRsyTb/"
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

