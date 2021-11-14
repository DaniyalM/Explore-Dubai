package com.dubaiculture.ui.postLogin.notifications.enums


enum class NotificationOptions(val id: Int, val displayValue: String, val resId: Int) {
    DELETE(0, "Delete", 0);

    companion object {
        fun fromId(id: Int): NotificationOptions {
            for (f in NotificationOptions.values()) {
                if (f.id == id) return f
            }
            throw IllegalArgumentException()
        }
    }
}