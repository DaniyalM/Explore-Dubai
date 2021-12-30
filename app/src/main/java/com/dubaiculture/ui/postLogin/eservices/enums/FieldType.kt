package com.dubaiculture.ui.postLogin.eservices.enums

enum class FieldType(val id: Int, val fieldType: String) {
    LABEL(0, "Label"),
    HINT(1, "Hint");
//    MESSAGE(2, "Message");

    companion object {
        fun fromName(valueType: String): FieldType? {
            for (f in values()) {
                if (f.fieldType == valueType) return f
            }
            return null
        }
    }
}