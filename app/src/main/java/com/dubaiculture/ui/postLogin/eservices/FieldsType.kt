package com.dubaiculture.ui.postLogin.eservices

enum class FieldsType(val id: Int, val valueType: String) {
    INPUT_TEXT(0, "TextBox"),
    INPUT_NUMBER(1, "TextBox"),
    DROP_DOWN(2, "Dropdown"),
    IMAGE(3, "IMAGE"),
    FILE(4, "File"),
    DATE(5, "Date"),
    TIME(6, "Time"),
    LABEL(7, "Label"),
    INPUT_TEXT_MULTILINE(8, "Multiline"),
    HINT(9, "Hint");


    companion object {
        fun fromId(id: Int): FieldsType {
            for (f in values()) {
                if (f.id == id) return f
            }
            throw IllegalArgumentException()
        }

        fun fromName(valueType: String): FieldsType {
            for (f in values()) {
                if (f.valueType == valueType) return f
            }
            throw IllegalArgumentException()
        }
    }

}