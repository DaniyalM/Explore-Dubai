package com.dubaiculture.data.repository.more.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetMessage(
      val  message : String,
      val  heading : String?="",
      val  reference : String?=""
):Parcelable