package com.nazartaraniuk.shopapplication.domain.usecases

import com.nazartaraniuk.shopapplication.domain.repository.ProductsRepository
import com.nazartaraniuk.shopapplication.presentation.adapters.DisplayableItem
import com.nazartaraniuk.shopapplication.presentation.common.ExploreFragmentUIComposer
import com.nazartaraniuk.shopapplication.presentation.mappers.ToUiModelMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetExplorePageUseCase @Inject constructor(
    private val repository: ProductsRepository,
    private val mapper: ToUiModelMapper,
    private val composer: ExploreFragmentUIComposer
) {
    suspend operator fun invoke() : Flow<Result<List<DisplayableItem>>> =
        withContext(Dispatchers.IO) {
            flow {
                val categories = repository.fetchCategories()
                if (categories.isFailure) {
                    val throwable = categories.exceptionOrNull()
                    emit(Result.failure(throwable ?: Exception("Error")))
                } else {
                    val listOfCategories = categories.getOrNull() ?: emptyList()
                    emit(
                        Result.success(
                            composer.composeInterface(
                                firstList = listOfCategories.map(mapper::toCategoryItemModel),
                            )
                        )
                    )
                }
            }
        }
}