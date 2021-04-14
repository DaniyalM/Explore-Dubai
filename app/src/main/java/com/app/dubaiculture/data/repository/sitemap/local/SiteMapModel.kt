package com.app.dubaiculture.data.repository.sitemap.local

data class SiteMapModel (
    val attractionID : String ?= null,
    val ibeconImg : String ? = null,
    val ibeconItems : List<IbeconITemsSiteMap>? = emptyList()
        )