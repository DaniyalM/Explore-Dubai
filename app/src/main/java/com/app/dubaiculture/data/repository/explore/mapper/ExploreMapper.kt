package com.app.dubaiculture.data.repository.explore.mapper

import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.data.repository.explore.local.models.UpComingEvents
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreDTO
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreResponse
import com.app.dubaiculture.data.repository.photo.local.Photo
import com.app.dubaiculture.data.repository.photo.remote.response.PhotoDTO


fun  transformExplore(exploreResponse: ExploreResponse): List<Explore> =
    exploreResponse.data.run {
        transformExplore(this)
    }

fun transformExplore(list: List<ExploreDTO>): List<Explore> = list.run {
    this.map {
        Explore(
            category = it.category,
            attractions = it.attractions,
            mustSee = it.mustSee,
            upcomingEvents = it.upcomingEvents,
            popularServices = it.popularServices,
            latestNews = it.latestNews
        )

    }
}
