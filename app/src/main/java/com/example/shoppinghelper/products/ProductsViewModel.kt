package com.example.shoppinghelper.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinghelper.auth.GoogleAuthUiClient
import com.example.shoppinghelper.data.Product
import com.example.shoppinghelper.data.ProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val productDao: ProductDao
) : ViewModel() {
    val user = googleAuthUiClient.getSignedInUser()
    val userId = user?.userId
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun signOut() {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
        }
    }

    fun getProducts(userId: String) = productDao.getProductsByUserId(userId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), emptyList())

    fun addProduct(userId: String, productName: String, productType: String, nfcId: String) = viewModelScope.launch {
        val product = Product(userId = userId, productName = productName, productType = productType, nfcID = nfcId)
        productDao.insert(product)
    }
}