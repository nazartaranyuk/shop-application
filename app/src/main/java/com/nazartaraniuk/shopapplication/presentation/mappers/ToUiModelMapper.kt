package com.nazartaraniuk.shopapplication.presentation.mappers

import com.nazartaraniuk.shopapplication.data.entities.ProductItemEntity
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.entities.Rating
import com.nazartaraniuk.shopapplication.presentation.models.CategoryItemModel
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel
import javax.inject.Singleton

@Singleton
class ToUiModelMapper {

    fun toProductItemModel(product: ProductItem): ProductItemModel {
        return ProductItemModel(
            category = product.category,
            description = product.description,
            id = product.id,
            image = product.image,
            price = product.price,
            rating = Rating(
                count = product.rating.count,
                rate = product.rating.rate
            ),
            title = product.title
        )
    }

    fun toCategoryItemModel(category: String) : CategoryItemModel {
        return CategoryItemModel(
            category = category
        )
    }

    fun toProductItemEntity(product: ProductItemModel): ProductItemEntity {
        return ProductItemEntity(
            category = product.category,
            description = product.description,
            id = product.id,
            image = product.image,
            price = product.price,
            rating = Rating(
                count = product.rating.count,
                rate = product.rating.rate
            ),
            title = product.title
        )
    }

    fun toProductItem(product: ProductItemModel): ProductItem {
        return ProductItem(
            category = product.category,
            description = product.description,
            id = product.id,
            image = product.image,
            price = product.price,
            rating = Rating(
                count = product.rating.count,
                rate = product.rating.rate
            ),
            title = product.title
        )
    }
}