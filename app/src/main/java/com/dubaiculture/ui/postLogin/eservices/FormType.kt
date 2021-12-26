package com.dubaiculture.ui.postLogin.eservices

enum class FormType(val id:Int,val value:String) {
    NOCFORM(0,"NOCForm"),
    BOOKINGESERVICE(1,"Booking E-Service"),
    RENTREQUESTFORM(2,"RentRequestForm"),
    CULTURALVISAFORM(2,"CulturalVisaForm"),
    SUPPLIERREGISTRATIONFORM(2,"SupplierRegistrtaionForm");


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