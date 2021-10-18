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
        location = it.location?:"",
        longitude = it.longitude?:"67.08119661055807",
        latitude = it.latitude?:"24.83250180519734",
        isFavourite = it.isFavourite,
        registrationDate = ""

    )