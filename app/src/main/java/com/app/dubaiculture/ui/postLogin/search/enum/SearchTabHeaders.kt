package com.app.dubaiculture.ui.postLogin.search.enum

import com.app.dubaiculture.ui.postLogin.more.enum.MoreNewsType

enum class SearchTabHeaders (var postion: Int) {
    ALL(0),
    NEWS(1),
    ATTRACTIONS(2),
    EVENTS(3),
    SERVICES(4);

    companion object {
        fun fromId(id: Int): SearchTabHeaders {
            for (f in values()) {
                if (f.postion == id) return f
            }
            throw IllegalArgumentException()
        }
        fun fromName(name: String): SearchTabHeaders {
            for (f in values()) {
                if (f.name == name) return f
            }
            throw IllegalArgumentException()
        }
    }
}