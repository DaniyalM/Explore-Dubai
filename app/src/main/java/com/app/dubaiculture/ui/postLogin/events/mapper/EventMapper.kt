package com.app.dubaiculture.ui.postLogin.events.mapper

import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.remote.request.EventFiltersRequestDTO
import com.app.dubaiculture.data.repository.event.remote.request.EventRequest
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel


fun transformBaseToEvent(it:  BaseModel) =
    Events(
        id = it.id,
        title = it.title,
        category = it.category,
        image = it.image,
        fromDate = it.fromDate,
        fromMonthYear = it.fromMonthYear,
        fromTime = it.fromTime,
        fromDay = it.fromDay,
        toDate = it.toDate,
        toMonthYear = it.toMonthYear,
        toTime = it.toTime,
        toDay = it.toDay,
        type = it.type,
        color = it.color ?: "",
        dateTo = it.dateTo,
        dateFrom = it.dateFrom,
        locationTitle = it.locationTitle,
        location = it.location,
        longitude = it.longitude,
        latitude = it.latitude,
        isFavourite = it.isFavourite,
    )