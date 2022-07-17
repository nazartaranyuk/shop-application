package com.nazartaraniuk.shopapplication.data.mappers

import com.nazartaraniuk.shopapplication.data.entities.ProductItemEntity
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.entities.Rating
import javax.inject.Singleton

@Singleton
object ApiResponseMapper {

    fun toProductItem(product: ProductItemEntity) : ProductItem {
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