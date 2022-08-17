package com.nazartaraniuk.shopapplication.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nazartaraniuk.shopapplication.domain.entities.Rating

@Entity(tableName= "favorites")
data class ProductItemEntity(
    val category: String,
    val description: String,
    @PrimaryKey val id: Int,
    val image: String,
    val price: Double,
    @Embedded
    val rating: Rating,
    val title: String,
    val test: Int = 0 // Test field for testing migrations
)