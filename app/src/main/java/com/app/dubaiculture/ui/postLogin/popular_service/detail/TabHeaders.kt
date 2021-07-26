package com.app.dubaiculture.ui.postLogin.popular_service.detail

enum class TabHeaders(var id: Int) {
    DESCRIPTION(0),
    PROCEDURE(1),
    REQUIREDDOCUMENTS(2),
    PAYMENTS(3),
    FAQS(4),
    TERMSANDCONDITIONS(5),
    SERVICESTART(6);

    companion object {
        fun fromId(id: Int): TabHeaders {
            for (tabs in values()) {
                if (tabs.id == id) return tabs
            }
            throw IllegalArgumentException()
        }
    }
}