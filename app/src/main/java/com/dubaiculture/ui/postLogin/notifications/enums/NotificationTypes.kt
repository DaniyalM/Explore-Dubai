package com.dubaiculture.ui.postLogin.notifications.enums

enum class NotificationTypes(val id: Int) {

    NA(0),
    GENERAL(1),
    EVENT(2),
    ATTRACTION(3),
    TRIP(4);

    companion object {
        fun shouldNavigateToAttractionDetail(notificationTypeId: Int) =
            (notificationTypeId == ATTRACTION.id)

        fun shouldNavigateToEventDetail(notificationTypeId: Int) =
            (notificationTypeId == EVENT.id)

        fun shouldNavigateToApplication(notificationTypeId: Int) =
            (notificationTypeId == NA.id)

        fun shouldNavigateToGeneralNotification(notificationTypeId: Int) =
            (notificationTypeId == GENERAL.id)

        fun shouldNavigateToTripNotification(notificationTypeId: Int) =
            (notificationTypeId == TRIP.id)

    }
}