package com.app.dubaiculture.data.repository.explore.mapper

import com.app.dubaiculture.data.repository.explore.local.models.UpComingEvents
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreDTO
import com.app.dubaiculture.data.repository.explore.remote.response.ExploreResponse
import com.app.dubaiculture.data.repository.photo.local.Photo
import com.app.dubaiculture.data.repository.photo.remote.response.PhotoDTO


fun transform(exploreResponse: ExploreResponse): List<UpComingEvents> =
    exploreResponse.data.run {
        transform(this)
    }

fun transform(list: List<ExploreDTO>): List<UpComingEvents> = list.run {
//    this.map { Photo(id = it.id!!, likes = it.likes!!, desc = it.desc!!) }
    this.map {
        UpComingEvents(
            id = it.id,
            is_liked = it.is_liked,
            title = it.title,
            category = it.category,
            location = it.location,
            start_date = it.start_date,
            end_date = it.end_date,
            price_tag = it.price_tag,
            image_url = it.image_url,
            date = it.date
        )
    }
}

