package com.dubaiculture.ui.postLogin.eservices.enums

enum class ValueType(val id: Int, val valueType: String) {
    INPUT_TEXT(0, "TextBox"),
    INPUT_NUMBER(1, "TextBox"),
    DROP_DOWN(2, "Dropdown"),
    IMAGE(3, "IMAGE"),
    FILE(4, "File"),
    DATE(5, "Date"),
    TIME(6, "Time"),
    INPUT_TEXT_MULTILINE(8, "Multiline"),

    //    BUTTON(9, "Button"),
    RADIO_BUTTON(10, "YesNo"),
//    HR_TAG(11, "HRtag"),
//    CREATED(12, "Created"),
//    ID(13, "ID")
    ;

    companion object {
        fun fromId(id: Int): ValueType {
            for (f in values()) {
                if (f.id == id) return f
            }
            throw IllegalArgumentException()
        }

        fun fromName(valueType: String): ValueType? {
            for (f in values()) {
                if (f.valueType == valueType) return f
            }
            return null
        }

        fun isInputField(valueType: String): Boolean {
            return (valueType == INPUT_TEXT.valueType
                    || valueType == INPUT_NUMBER.valueType
                    || valueType == INPUT_TEXT_MULTILINE.valueType)
        }
    }

}