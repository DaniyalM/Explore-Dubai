package com.dubaiculture.ui.postLogin.eservices

enum class FieldsTypeMode(val id: Int, val fieldType: String) {
    INPUT_TEXT(0, "Label"),
    INPUT_NUMBER(1, "Hint");


    companion object {


        fun fromName(valueType: String): FieldsTypeMode {
            for (f in values()) {
                if (f.fieldType == valueType) return f
            }
            throw IllegalArgumentException()
        }
    }
}