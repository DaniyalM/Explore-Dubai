package com.app.dubaiculture.data.repository.login.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UaeLoginRequest(
    @PrimaryKey
    val idn: String,
    val email: String,
    val mobile: String,
    val titleAr: String,
    val titleEn: String,
    val firstNameEn: String,
    val firstNameAr: String,
    val lastNameEn: String,
    val lastNameAr: String,
    val fullNameEn: String,
    val fullNameAr: String,
    val nationalityEn: String,
    val nationalityAr: String,
    val user_type: String,
    val gender: String,
    val acr: String,
    val sub: String,
    val idType: String,
    val spuuid: String,
//    val amr: List<String>,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
)


//{
//    "email": "string",
//    "mobile": "string",
//    "titleAR": "string",
//    "titleEN": "string",
//    "firstnameAR": "string",
//    "firstnameEN": "string",
//    "lastnameAR": "string",
//    "lastnameEN": "string",
//    "fullnameAR": "string",
//    "fullnameEN": "string",
//    "nationalityAR": "string",
//    "nationalityEN": "string",
//    "userType": "string",
//    "gender": "string",
//    "idn": "string",
//    "acr": "string",
//    "sub": "string",
//    "idType": "string",
//    "spuuid": "string",
//    "amr": [
//    "string"
//    ]
//}