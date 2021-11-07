package com.dubaiculture.ui.components.custombutton

enum class ButtonType(var id: Int) {

    FILLED(0),
    OUTLINED(1);

    companion object {
        fun fromId(id: Int): ButtonType {
            for (f in values()) {
                if (f.id == id) return f
            }
            throw IllegalArgumentException()
        }
    }
}