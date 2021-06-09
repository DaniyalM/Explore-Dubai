package com.app.dubaiculture.data.repository.more.mapper

import com.app.dubaiculture.data.repository.attraction.local.models.SocialLink
import com.app.dubaiculture.data.repository.more.local.*
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequest
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequestDTO
import com.app.dubaiculture.data.repository.more.remote.response.*

fun transformPrivacyAndTermsRequest(privacyAndTermRequest: PrivacyAndTermRequest) =
    PrivacyAndTermRequestDTO(
        culture = privacyAndTermRequest.culture
    )

fun transformPrivacyResponse(moreresponse: MoreResponse) =
    moreresponse.Result.PrivacyPolicy[0].run {
        PrivacyPolicy(
            title = Title!!,
            description = Description!!,
        )
    }

fun transformTermsAndConditionsResponse(moreresponse: MoreResponse) =
    moreresponse.Result.TermsAndCondition[0].run {
        PrivacyPolicy(
            title = Title!!,
            description = Description!!,
        )
    }

fun transformCultureConnoisseur(moreresponse: MoreResponse)=
        moreresponse.Result.run {
            CultureConnoisseur(
                    description = cultureConnoisseur.Description,
                    dubaiLogo = cultureConnoisseur.DubaiLogo,
                    govtLogo = cultureConnoisseur.GovtLogo,
                    readMoreTitle = cultureConnoisseur.ReadMoreTitle,
                    readMoreURL = cultureConnoisseur.ReadMoreURL,
                    title = cultureConnoisseur.Title
            )
        }



fun transformationContactCenter(moreresponse: MoreResponse) = moreresponse.Result.run {
    ContactCenter(
        title = this.Title,
        description = this.Description,
        contactCenterLocation = transformContactCenterLocation(ContactCenterLocation),
        contactCenterReach = transformContactCenterReach(ContactCenterReach),
        contactCenterFeedback = transformContactCenterFeedback(ContactCenterFeedback),
        contactCenterSuggestionComplains = transformContactCenterSuggestionComplains(
            ContactCenterSuggestionComplains
        ),
        socialLinks = SocialLinks.let {
            it.map {
                SocialLink(
                    facebookPageLink = it.facebookPageLink.toString(),
                    facebookIcon = it.facebookIcon.toString(),
                    instagramIcon = it.instagramIcon,
                    instagramPageLink = it.instagramPageLink.toString()
                )
            }
        }

    )
}

fun transformContactCenterLocation(contactCenterLocationDTO: ContactCenterLocationDTO) =
    ContactCenterLocation(
        subtitle = contactCenterLocationDTO.Subtitle,
        day = contactCenterLocationDTO.Day,
        time = contactCenterLocationDTO.Time,
        houseContent = contactCenterLocationDTO.HouseContent,
        directionContent = contactCenterLocationDTO.DirectionContent,
        pinContent = contactCenterLocationDTO.PinContent,
        mapLatitude = contactCenterLocationDTO.MapLatitude,
        mapLongitude = contactCenterLocationDTO.MapLongitude
    )

fun transformContactCenterReach(contactCenterReachDTO: ContactCenterReachDTO) =
    ContactCenterReach(
        subTitle = contactCenterReachDTO.Subtitle,
        callTitle = contactCenterReachDTO.CallTitle,
        callContent = contactCenterReachDTO.CallContent,
        emailContent = contactCenterReachDTO.EmailContent,
        emailTitle = contactCenterReachDTO.EmailTitle,
        faxTitle = contactCenterReachDTO.FaxTitle,
        faxContent = contactCenterReachDTO.FaxContent,
        websiteContent = contactCenterReachDTO.WebsiteContent,
        websiteTitle = contactCenterReachDTO.WebsiteTitle,

        )

fun transformContactCenterFeedback(contactCenterFeedbackDTO: ContactCenterFeedbackDTO) =
    ContactCenterFeedback(
        title = contactCenterFeedbackDTO.Title,
        subTitle = contactCenterFeedbackDTO.Subtitle
    )

fun transformContactCenterSuggestionComplains(contactCenterSuggestionComplainsDTO: ContactCenterSuggestionComplainsDTO) =
    ContactCenterSuggestionComplains(
        image1 = contactCenterSuggestionComplainsDTO.Image1,
        image1Url = contactCenterSuggestionComplainsDTO.Image1URL,
        image2 = contactCenterSuggestionComplainsDTO.Image2,
        image2Url = contactCenterSuggestionComplainsDTO.Image2URL,
        subTitle = contactCenterSuggestionComplainsDTO.Subtitle
    )


fun  transformFAQsRequest(moreresponse: MoreResponse)=
    moreresponse.Result.run {
        FAQs(
            faqTitle = FaqTitle,
            faqItems = FaqItems.map {
                FaqItem(
                    answer = it.Answer,
                    question = it.Question
                )
            }
        )
    }
