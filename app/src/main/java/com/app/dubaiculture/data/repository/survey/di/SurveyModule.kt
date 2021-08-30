package com.app.dubaiculture.data.repository.survey.di

import com.app.dubaiculture.data.repository.ApplicationDatabase
import com.app.dubaiculture.data.repository.profile.local.ProfileDao
import com.app.dubaiculture.data.repository.profile.service.ProfileService
import com.app.dubaiculture.data.repository.survey.service.SurveyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object SurveyModule {
    @Provides
    fun provideSurveyService(retrofit: Retrofit): SurveyService =
        retrofit.create(SurveyService::class.java)

}