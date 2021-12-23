package com.dubaiculture.ui.postLogin.eservices

enum class ValueType(val id: Int, val valueType: String) {
    INPUT_TEXT(0, "TextBox"),
    INPUT_NUMBER(1, "TextBox"),
    DROP_DOWN(2, "Dropdown"),
    IMAGE(3, "IMAGE"),
    FILE(4, "File"),
    DATE(5, "Date"),
    TIME(6, "Time"),
    INPUT_TEXT_MULTILINE(8, "Multiline");

    companion object {
        fun fromId(id: Int): ValueType {
            for (f in values()) {
                if (f.id == id) return f
            }
            throw IllegalArgumentException()
        }

        fun fromName(valueType: String): ValueType {
            for (f in values()) {
                if (f.valueType == valueType) return f
            }
            throw IllegalArgumentException()
        }

        fun isInputField(valueType: String): Boolean {
            return (valueType == INPUT_TEXT.valueType
                    || valueType == INPUT_NUMBER.valueType
                    || valueType == INPUT_TEXT_MULTILINE.valueType
                    || valueType == DATE.valueType
                    || valueType == TIME.valueType
                    || valueType == DROP_DOWN.valueType)
        }
    }

}