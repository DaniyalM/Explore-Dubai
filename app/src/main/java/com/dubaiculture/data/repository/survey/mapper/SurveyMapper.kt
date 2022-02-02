package com.dubaiculture.data.repository.survey.mapper

import com.dubaiculture.data.repository.more.local.GetMessage
import com.dubaiculture.data.repository.more.remote.response.MoreResponse
import com.dubaiculture.data.repository.survey.request.Form
import com.dubaiculture.data.repository.survey.request.Items
import com.dubaiculture.data.repository.survey.remote.response.SurveyFormResponse
import com.dubaiculture.data.repository.survey.request.FormDTO
import com.dubaiculture.data.repository.survey.request.ItemsDTO


fun transformSurvey(surveyFormResponse: SurveyFormResponse) : Form =

    surveyFormResponse.result.Form.let {
        Form(
            title = it.Title,
            subtitle = it.Subtitle,
            formID = it.FormID,
            formName = it.FormName?:"",
            items = it.Items.mapIndexed { index, itemsDTO ->
                Items(
                    id = itemsDTO.ID?:"",
                    question = itemsDTO.Question?:"",
                    input = itemsDTO.Input?:"Textbox",
                    answer = itemsDTO.Answer?:"",
                    index=index
                )
            },
            itemId = ""
        )
    }

fun transformFormIntoDTO(form: Form) : FormDTO =
    FormDTO(
        ItemId = form.itemId?:"",
        FormID = form.formID,
        Items = form.items.map {
            ItemsDTO(
                QuestionId = it.id,
                Answer = it.answer
            )
        },
        Culture = form.culture,
        Title = "",
        Subtitle = ""
    )


fun transformPostSurveyMessage(surveyFormResponse: SurveyFormResponse) =
    surveyFormResponse.result.run {
        GetMessage(
            message = Added
        )
    }




