package com.dubaiculture.ui.postLogin.search.enum

enum class SearchTabHeaders(var postion: Int) {
    ALL(0),
    ATTRACTIONS(1),
    EVENTS(2),
    SERVICES(3),
    NEWS(4),
    OTHERS(5);

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