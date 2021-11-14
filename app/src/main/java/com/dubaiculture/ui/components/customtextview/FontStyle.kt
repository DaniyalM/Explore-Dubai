package com.dubaiculture.ui.components.customtextview

enum class FontStyle(var id: Int) {

    LIGHT(0),
    REGULAR(1),
    BOLD(2);

    companion object {
        fun fromId(id: Int): FontStyle {
            for (f in values()) {
                if (f.id == id) return f
            }
            throw IllegalArgumentException()
        }
    }
}