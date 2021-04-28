package com.app.dubaiculture.data.repository.viewgallery.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.app.dubaiculture.data.repository.viewgallery.local.Result
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ViewGalleryResponse constructor(@SerializedName("Result")
                                          @Expose
                                          val Result: Result
) : BaseResponse()