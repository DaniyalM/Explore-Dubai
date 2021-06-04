package com.app.dubaiculture.data.repository.event.mapper

import com.app.dubaiculture.data.repository.attraction.local.models.SocialLink
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.event.local.models.Filter
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventSchedule
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItems
import com.app.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItemsSlots
import com.app.dubaiculture.data.repository.event.remote.request.*
import com.app.dubaiculture.data.repository.event.remote.response.EventResponse
import com.app.dubaiculture.data.repository.event.remote.response.EventsDTO
import com.app.dubaiculture.data.repository.event.remote.response.FilterDTO

// Add To Favourite Request is used for both Attractions and events from below
fun transformAddToFavouriteRequest(addToFavouriteRequest: AddToFavouriteRequest) =
        AddToFavouriteRequestDTO(
                UserID = addToFavouriteRequest.userId,
                ItemID = addToFavouriteRequest.itemId,
                Type = addToFavouriteRequest.type!!
        )

// Add To Favourite Request is used for both Attractions and events from above
fun transformEventFiltersRequest(eventRequest: EventRequest) =
        EventFiltersRequestDTO(
                Culture = eventRequest.culture!!,
//        UserID = eventRequest.userId,
                Category = eventRequest.category,
                Keyword = eventRequest.keyword,
                DateFrom = eventRequest.dateFrom,
                DateTo = eventRequest.dateTo,
                Type = eventRequest.type,
                Location = eventRequest.location

        )

fun transformEventDetailRequest(eventDetailRequest: EventRequest) =
        EventDetailRequestDTO(
                id = eventDetailRequest.eventId!!,
                culture = eventDetailRequest.culture!!
        )

//fun transformEventDetailRequest1(eventDetailRequest: Schedule) =
//    EventDetailRequestDTO(
//        id = eventDetailRequest.eventId!!,
//        culture = eventDetailRequest.culture!!
//    )


fun transformOtherEvents(eventResponse: EventResponse): ArrayList<Events> =
        eventResponse.Result.otherEvents.run {
            transformOtherEventList(this)
        }


fun transformationRadioList(eventResponse: EventResponse): ArrayList<Filter> =
        eventResponse.Result.radioGroupList.run {
            transformRadioBtnList(this)
        }

fun transformRadioBtnList(filter: ArrayList<FilterDTO>): ArrayList<Filter> =
        filter.map {
            Filter(
                    id = it.id,
                    title = it.title
            )
        } as ArrayList<Filter>


fun transformationCategoryList(eventResponse: EventResponse): ArrayList<Filter> =
        eventResponse.Result.categoryList.run {
            transformcategoryList(this)
        }

fun transformcategoryList(filter: ArrayList<FilterDTO>): ArrayList<Filter> =
        filter.map {
            Filter(
                    id = it.id,
                    title = it.title
            )
        } as ArrayList<Filter>


fun transformationlocationList(eventResponse: EventResponse): ArrayList<Filter> =
        eventResponse.Result.locationList.run {
            transformlocationList(this)
        }

fun transformlocationList(filter: ArrayList<FilterDTO>): ArrayList<Filter> =
        filter.map {
            Filter(
                    id = it.id,
                    title = it.title
            )
        } as ArrayList<Filter>


fun transformEventList(events: List<EventsDTO>): List<Events> =
        events.map {
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
                    registrationDate = it.registrationDate,
//            color=it.color,
                    dateTo = it.dateTo,
                    dateFrom = it.dateFrom,
                    locationTitle = it.locationTitle,
                    location = it.location,
                    longitude = it.longitude,
                    latitude = it.latitude,
                    isFavourite = it.isFavourite,
            )
        }

fun transformOtherEventList(events: ArrayList<EventsDTO>): ArrayList<Events> =
        events.map {
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
                    registrationDate = it.registrationDate,
//            color=it.color,
                    dateTo = it.dateTo,
                    dateFrom = it.dateFrom,
                    locationTitle = it.locationTitle,
                    location = it.location,
                    longitude = it.longitude,
                    latitude = it.latitude,
                    isFavourite = it.isFavourite,
            )
        } as ArrayList<Events>


fun transformFeaturedEvents(eventResponse: EventResponse): ArrayList<Events> =
        eventResponse.Result.featuredEvents.run {
            transformFeatureEventList(this)
        }

fun transformFeatureEventList(featureEvents: ArrayList<EventsDTO>): ArrayList<Events> =
        featureEvents.map {
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
                    emailContact = it.emailContact,
                    numberContact = it.numberContact
            )
        } as ArrayList<Events>


fun transformHomeEventListingRequest(eventListRequest: EventRequest) =
        HomeEventListRequestDTO(
                culture = eventListRequest.culture!!
        )


fun transformEventDetail(eventResponse: EventResponse): Events {
    return transformEventDetail(eventResponse.Result.event)
}

fun transformEventDetail(eventDTO: EventsDTO): Events = Events(
        id = eventDTO.id,
        title = eventDTO.title,
        category = eventDTO.category,
        image = eventDTO.image,
        desc = eventDTO.desc,
        fromDate = eventDTO.fromDate,
        fromMonthYear = eventDTO.fromMonthYear,
        fromTime = eventDTO.fromTime,
        fromDay = eventDTO.fromDay,
        toDate = eventDTO.toDate,
        toMonthYear = eventDTO.toMonthYear,
        toTime = eventDTO.toTime,
        toDay = eventDTO.toDay,
        type = eventDTO.type,
        color = eventDTO.color ?: "",
        dateTo = eventDTO.dateTo,
        dateFrom = eventDTO.dateFrom,
        registrationDate = eventDTO.registrationDate,
        locationTitle = eventDTO.locationTitle,
        location = eventDTO.location,
        longitude = eventDTO.longitude,
        latitude = eventDTO.latitude,
        emailContact = eventDTO.emailContact,
        numberContact = eventDTO.numberContact,
        isFavourite = eventDTO.isFavourite,
        socialLink = eventDTO.socialLinks?.let {
            it.map {
                SocialLink(
                        facebookPageLink = it.facebookPageLink.toString(),
                        facebookIcon = it.facebookIcon.toString(),
                        instagramIcon = it.instagramIcon,
                        instagramPageLink = it.instagramPageLink.toString(),
                        twitterIcon = it.twitterIcon,
                        twitterPageLink = it.twitterPageLink,
                        youtubeIcon = it.youtubeIcon,
                        youtubePageLink = it.youtubePageLink,
                        linkedInIcon = it.linkedInIcon,
                        linkedInPageLink = it.linkedInPageLink
                )
            }
        },
        eventSchedule = eventDTO.eventSchedule.map {
            EventSchedule(
                    id = it.id,
                    description = it.description,
                    eventScheduleItems = it.eventScheduleItems.map {
                        EventScheduleItems(
                                date = it.date,
                                eventScheduleItemsSlots = it.eventScheduleItemsTimeSlots.map {
                                    EventScheduleItemsSlots(
                                            summary = it.summary,
                                            timeFrom = it.timeFrom,
                                            timeTo = it.timeTo
                                    )
                                }
                        )
                    }
            )
        },
        relatedEvents = eventDTO.relatedEvents.map {
            Events(
                    id = eventDTO.id,
                    title = eventDTO.title,
                    category = eventDTO.category,
                    image = eventDTO.image,
                    fromDate = eventDTO.fromDate,
                    fromMonthYear = eventDTO.fromMonthYear,
                    fromTime = eventDTO.fromTime,
                    fromDay = eventDTO.fromDay,
                    toDate = eventDTO.toDate,
                    toMonthYear = eventDTO.toMonthYear,
                    toTime = eventDTO.toTime,
                    toDay = eventDTO.toDay,
                    type = eventDTO.type,
                    color = eventDTO.color ?: "",
                    dateTo = eventDTO.dateTo,
                    dateFrom = eventDTO.dateFrom,
                    locationTitle = eventDTO.locationTitle,
                    location = eventDTO.location,
                    longitude = eventDTO.longitude,
                    latitude = eventDTO.latitude,
                    isFavourite = eventDTO.isFavourite,
            )
        }


)

