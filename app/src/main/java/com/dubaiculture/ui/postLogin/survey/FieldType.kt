package com.dubaiculture.ui.postLogin.survey

import com.dubaiculture.ui.postLogin.eservices.FieldsType


enum class FieldType(val id :Int, val valueType: String) {
    YesNo(0,"YesNo"),
    Textbox(1,"Textbox"),
    Rating(2,"Rating");

    companion object{
            fun fromId(id: Int): FieldType {
                for (f in values()) {
                    if (f.id == id) return f
                }
                throw IllegalArgumentException()
            }

        fun fromName(valueType: String): FieldType {
            for (f in values()) {
                if (f.valueType == valueType) return f
            }
            throw IllegalArgumentException()
        }
    }
}