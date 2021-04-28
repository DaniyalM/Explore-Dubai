package com.app.dubaiculture.data.repository.viewgallery.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Images(
    val image : String?=null
) : Parcelable