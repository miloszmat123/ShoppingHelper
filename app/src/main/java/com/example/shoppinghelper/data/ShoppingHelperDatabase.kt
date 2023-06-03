package com.example.shoppinghelper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinghelper.products.Product
import com.example.shoppinghelper.products.ProductDao

@Database(entities = [Product::class], version = 1)
abstract class ShoppingHelperDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var Instance: ShoppingHelperDatabase? = null

        fun getDatabase(context: Context): ShoppingHelperDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ShoppingHelperDatabase::class.java,
                    "shopping_helper_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}