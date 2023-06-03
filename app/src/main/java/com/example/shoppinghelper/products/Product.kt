package com.example.shoppinghelper.products

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class Product (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val productName: String,
    val productType: String,
    val nfcID: String
)