package com.app.dubaiculture.data.repository.sitemap.remote.response

import com.app.dubaiculture.data.repository.base.BaseResponse
import com.app.dubaiculture.data.repository.sitemap.local.Result
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SiteMapResponse constructor(@SerializedName("Result")
@Expose
val Result: Result
    ) : BaseResponse()