package com.example.shoppinghelper.products

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM products WHERE userId = :userId")
    fun getProductsByUserId(userId: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE nfcId = :nfcId")
    fun getProductsByNfcId(nfcId: String): Flow<List<Product>>
}