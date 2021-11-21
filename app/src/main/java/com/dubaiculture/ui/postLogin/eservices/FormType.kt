package com.dubaiculture.ui.postLogin.eservices

enum class FormType(val id:Int,val value:String) {
    NOCFORM(0,"NOCForm"),
    BOOKINGESERVICE(1,"BookingEService"),
    RENTREQUESTFORM(2,"RentRequestForm"),
    CULTURALVISAFORM(2,"CulturalVisaForm"),
    SUPPLIERREGISTRATIONFORM(2,"SupplierRegistrationForm");


    companion object {
        fun fromId(id: Int): FormType {
            for (f in values()) {
                if (f.id == id) return f
            }
            throw IllegalArgumentException()
        }

        fun fromName(valueType: String): FormType {
            for (f in values()) {
                if (f.value == valueType) return f
            }
            throw IllegalArgumentException()
        }
    }
}