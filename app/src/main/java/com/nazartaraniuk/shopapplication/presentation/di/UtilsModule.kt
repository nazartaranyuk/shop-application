package com.nazartaraniuk.shopapplication.presentation.di

import com.nazartaraniuk.shopapplication.data.mappers.ApiResponseMapper
import com.nazartaraniuk.shopapplication.domain.mappers.DomainMapper
import com.nazartaraniuk.shopapplication.presentation.common.ExploreFragmentUIComposer
import com.nazartaraniuk.shopapplication.presentation.common.HomeFragmentUIComposer
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {

    @Provides
    fun provideUiMapper() : ToUiModelMapper {
        return ToUiModelMapper()
    }

    @Provides
    fun provideDomainMapper() : DomainMapper {
        return DomainMapper()
    }

    @Provides
    fun providesApiResponseMapper() : ApiResponseMapper {
        return ApiResponseMapper()
    }

    @Provides
    fun provideHomeComposer() : HomeFragmentUIComposer {
        return HomeFragmentUIComposer
    }

    @Provides
    fun provideExploreComposer() : ExploreFragmentUIComposer {
        return ExploreFragmentUIComposer
    }
}