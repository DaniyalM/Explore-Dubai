package com.app.dubaiculture.data.repository.setpassword.di
import com.app.dubaiculture.data.repository.setpassword.service.SetPasswordService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object SetPasswordModule {
    @Provides
    fun provideSetPasswordService(retrofit: Retrofit): SetPasswordService =
        retrofit.create(SetPasswordService::class.java)
}