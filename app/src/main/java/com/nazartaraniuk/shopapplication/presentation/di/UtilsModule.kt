package com.nazartaraniuk.shopapplication.presentation.di

import com.nazartaraniuk.shopapplication.data.mappers.ApiResponseMapper
import com.nazartaraniuk.shopapplication.presentation.common.GUIComposer
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {

    @Provides
    fun provideUiMapper() : ToUiModelMapper {
        return ToUiModelMapper
    }

    @Provides
    fun providesApiResponseMapper() : ApiResponseMapper {
        return ApiResponseMapper
    }

    @Provides
    fun provideComposer() : GUIComposer {
        return GUIComposer
    }
}