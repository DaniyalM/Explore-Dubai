package com.dubaiculture.ui.postLogin.eservices

enum class FieldType(val id: Int, val fieldType: String) {
    LABEL(0, "Label"),
    HINT(1, "Hint");


    companion object {


        fun fromName(valueType: String): FieldType {
            for (f in values()) {
                if (f.fieldType == valueType) return f
            }
            throw IllegalArgumentException()
        }
    }
}