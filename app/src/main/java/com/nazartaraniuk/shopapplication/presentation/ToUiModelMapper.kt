package com.nazartaraniuk.shopapplication.presentation

import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.entities.Rating
import com.nazartaraniuk.shopapplication.presentation.models.ProductItemModel

object ToUiModelMapper {

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
}