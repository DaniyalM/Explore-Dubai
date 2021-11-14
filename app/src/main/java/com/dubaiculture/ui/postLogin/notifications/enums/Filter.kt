package com.app.neomads.ui.postLogin.notifications.enums

enum class Filter(val id: Int, val displayValue: String) {
    ALL(0, "All"),
    FEEDS(1, "Feeds"),
    MARKETPLACE(2, "Marketplace");

    companion object {
        fun fromId(id: Int): Filter {
            for (f in Filter.values()) {
                if (f.id == id) return f
            }
            throw IllegalArgumentException()
        }
    }
}