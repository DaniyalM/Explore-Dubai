import com.app.dubaiculture.data.repository.login.local.UaeLoginRequest
import com.app.dubaiculture.data.repository.login.remote.request.LoginRequest

import com.app.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.request.UaeLoginRequestDTO
import com.app.dubaiculture.data.repository.login.remote.request.changedpass.ChangedPassRequest
import com.app.dubaiculture.data.repository.login.remote.request.changedpass.ChangedPassRequestDTO


fun transformUaeRequest(uaeLoginRequest: UaeLoginRequest)= UaeLoginRequestDTO(
    email = uaeLoginRequest.email,
    mobile = uaeLoginRequest.mobile,
    titleAR = uaeLoginRequest.titleAr,
    titleEN = uaeLoginRequest.titleEn,
    firstnameAR = uaeLoginRequest.firstNameAr,
    lastnameAR = uaeLoginRequest.lastNameAr,
    fullnameAR = uaeLoginRequest.fullNameAr,
    fullnameEN = uaeLoginRequest.fullNameEn,
    nationalityAR = uaeLoginRequest.nationalityAr,
    nationalityEN = uaeLoginRequest.nationalityEn,
    userType = uaeLoginRequest.user_type,
    gender = uaeLoginRequest.gender,
    idn = uaeLoginRequest.idn,
    acr = uaeLoginRequest.acr,
    sub = uaeLoginRequest.sub,
    idType = uaeLoginRequest.idType,
    spuuid = uaeLoginRequest.spuuid,
    amr = mutableListOf(),
    firstnameEN = uaeLoginRequest.firstNameEn,
    lastnameEN = uaeLoginRequest.lastNameEn
)

//val email:String,
//val mobile:String,
//val titleAR:String,
//val titleEN:String,
//val firstnameEN:String,
//val firstnameAR:String,
//val lastnameEN:String,
//val lastnameAR:String,
//val fullnameEN:String,
//val fullnameAR:String,
//val nationalityEN:String,
//val nationalityAR:String,
//val userType:String,
//val gender:String,
//val idn:String,
//val acr:String,
//val sub:String,
//val idType:String,
//val spuuid:String,
//val amr:List<String>,

fun transform(loginRequest: LoginRequest): LoginRequestDTO {
    return LoginRequestDTO(
        PhoneNumber = loginRequest.phoneNumber,
        Password = loginRequest.password,
        Email = loginRequest.email
    )
}

fun transformChangedPass(changedPassRequest: ChangedPassRequest): ChangedPassRequestDTO {
    return ChangedPassRequestDTO(
        OldPassword = changedPassRequest.oldPass,
        NewPassword = changedPassRequest.newPass,
        ConfirmPassword = changedPassRequest.confirmPass
    )

}
