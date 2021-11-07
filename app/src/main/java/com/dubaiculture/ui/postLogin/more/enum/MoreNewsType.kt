package com.dubaiculture.ui.postLogin.more.enum

enum class MoreNewsType(var postion: Int) {
    NEWS(0),
    CONTACT(1),
    FAQS(2),
    RELATED_APP(3),
    PRIVACY(4),
    TERM_CONDITION(5);
    companion object {
        fun fromId(id: Int): MoreNewsType {
            for (f in values()) {
                if (f.postion == id) return f
            }
            throw IllegalArgumentException()
        }
        fun fromName(name: String): MoreNewsType {
            for (f in values()) {
                if (f.name == name) return f
            }
            throw IllegalArgumentException()
        }
    }
}