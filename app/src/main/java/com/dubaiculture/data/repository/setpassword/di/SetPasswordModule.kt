package com.dubaiculture.data.repository.setpassword.di
import com.dubaiculture.data.repository.setpassword.service.SetPasswordService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object SetPasswordModule {
    @Provides
    fun provideSetPasswordService(retrofit: Retrofit): SetPasswordService =
        retrofit.create(SetPasswordService::class.java)
}