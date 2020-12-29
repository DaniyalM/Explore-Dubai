package com.app.dubaiculture.di

import android.content.Context
import com.app.dubaiculture.utils.security.lds.AES
import com.app.dubaiculture.utils.security.lds.RSA
import com.app.dubaiculture.utils.security.lds.SecurityManagerLDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object SecurityManagerLDSModule {

    @Singleton
    @Provides
    fun provideSecurityManagerLDS(@ApplicationContext context: Context): SecurityManagerLDS {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            AES()
        } else {
            RSA(context)
        }
    }

}