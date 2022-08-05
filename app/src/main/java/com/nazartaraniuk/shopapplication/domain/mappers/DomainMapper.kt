package com.nazartaraniuk.shopapplication.domain.mappers

import com.nazartaraniuk.shopapplication.data.entities.ProductItemEntity
import com.nazartaraniuk.shopapplication.domain.entities.ProductItem
import com.nazartaraniuk.shopapplication.domain.entities.Rating

class DomainMapper {

    fun toProductItemEntity(product: ProductItem) : ProductItemEntity {
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
}