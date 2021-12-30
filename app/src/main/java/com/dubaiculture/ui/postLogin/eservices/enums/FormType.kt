package com.dubaiculture.ui.postLogin.eservices.enums

enum class FormType(val id: Int, val value: String, val url: String) {
    NOC_FORM(0, "NOCForm", "NOC/CreateNOC"),
    BOOKING_ESERVICE(1, "Booking E-Service", "Booking/CreateBooking"),
    RENT_REQUEST(2, "RentRequestForm", "Request/CreateRentRequest"),
    CULTURAL_VISA(3, "CulturalVisaForm", "CulturalVisa/CreateCulturalVisa"),
    SUPPLIER_REGISTRATION(4, "SupplierRegistrationForm", "Supplier/SupplierRegistration");

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