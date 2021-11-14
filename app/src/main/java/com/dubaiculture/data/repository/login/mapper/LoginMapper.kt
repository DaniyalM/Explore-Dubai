import com.dubaiculture.data.repository.login.local.UAEPass
import com.dubaiculture.data.repository.login.remote.request.LoginRequest

import com.dubaiculture.data.repository.login.remote.request.LoginRequestDTO
import com.dubaiculture.data.repository.login.remote.request.UAELoginRequest
import com.dubaiculture.data.repository.login.remote.request.UaeLoginRequestDTO
import com.dubaiculture.data.repository.login.remote.request.changedpass.ChangedPassRequest
import com.dubaiculture.data.repository.login.remote.request.changedpass.ChangedPassRequestDTO
import com.dubaiculture.data.repository.login.remote.response.UAEPassDTO


fun transformUaeRequest(uaeLoginRequest: UAELoginRequest) = UaeLoginRequestDTO(
    AccessToken = uaeLoginRequest.token?:"",
    DefaultCulture = uaeLoginRequest.culture?:"en",
    Email = uaeLoginRequest.email?:"",
    Password = uaeLoginRequest.password?:""

)

fun transformUaeResponse(uaePassDTO: UAEPassDTO) = UAEPass(
    titleAr = uaePassDTO.titleAR?:"",
    titleEn = uaePassDTO.titleEN?:"",
    firstNameAr = uaePassDTO.firstnameAR?:"",
    lastNameEn = uaePassDTO.lastnameEN?:"",
    fullNameAr = uaePassDTO.fullnameAR?:"",
    fullNameEn = uaePassDTO.fullnameEN?:"",
    nationalityAr = uaePassDTO.nationalityAR?:"",
    nationalityEn = uaePassDTO.nationalityEN?:"",
    user_type = uaePassDTO.userType?:"",
    gender = uaePassDTO.gender?:"",
    idn = uaePassDTO.idn?:"",
    sub = uaePassDTO.sub?:"",
    idType = uaePassDTO.idType?:"",
    spuuid = uaePassDTO.spuuid?:"",
    firstNameEn = uaePassDTO.firstnameEN?:"",
    lastNameAr = uaePassDTO.lastnameAR?:"",
    email = uaePassDTO.email?:"",
    mobile = uaePassDTO.mobile?:"",
    uuid = uaePassDTO.uuid?:""

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
