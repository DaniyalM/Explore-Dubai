package com.app.dubaiculture.data.repository.explore.remote.response

import com.app.dubaiculture.data.repository.explore.local.models.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ExploreDTO(
    var category: String,
    var attractions: List<Attraction>,
    var mustSee: List<MustSee>,
    var upcomingEvents: List<UpComingEvents>,
    var popularServices: List<PopularServices>,
    var latestNews: List<LatestNews>
)

