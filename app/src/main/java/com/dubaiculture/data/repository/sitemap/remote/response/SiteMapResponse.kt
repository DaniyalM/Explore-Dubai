package com.dubaiculture.data.repository.sitemap.remote.response

import com.dubaiculture.data.repository.base.BaseResponse
import com.dubaiculture.data.repository.sitemap.local.Result
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SiteMapResponse constructor(@SerializedName("Result")
@Expose
val Result: Result
    ) : BaseResponse()