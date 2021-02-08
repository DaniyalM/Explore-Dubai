package com.app.dubaiculture.data.repository.explore.mapper

import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreDTO
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreResponse


fun transformExplore(exploreResponse: ExploreResponse): List<Explore> =
    exploreResponse.Result.value.run {
        transformExplore(this)
    }

fun transformExplore(list: List<ExploreDTO>): List<Explore> = list.run {
    this.map {
        Explore(
            title = it.title,
            category = it.category,
            value = it.value
        )
    }
}
