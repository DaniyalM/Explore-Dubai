package com.dubaiculture.data.repository.viewgallery.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(
    val image : String?=null
) : Parcelable