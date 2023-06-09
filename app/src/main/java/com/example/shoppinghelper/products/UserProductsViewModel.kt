package com.example.shoppinghelper.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinghelper.auth.GoogleAuthUiClient
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserProductsViewModel(
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val productDao: ProductDao
) : ViewModel() {
    val user = googleAuthUiClient.getSignedInUser()

    val products = productDao.getAllProducts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), emptyList())
    val productsByUserId = productDao.getProductsByUserId(user?.userId ?: "").stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), emptyList())
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun signOut() {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
        }
    }

    fun addProduct(userId: String, productName: String, productType: String, nfcId: String) = viewModelScope.launch {
        val product = Product(userId = userId, productName = productName, productType = productType, nfcId = nfcId)
        productDao.insert(product)
    }

    fun deleteProduct(product: Product) = viewModelScope.launch {
        productDao.delete(product)
    }

    fun updateProduct(id: Int, userId: String, productName: String, productType: String, nfcId: String) = viewModelScope.launch {
        val product = Product(id = id, userId = userId, productName = productName, productType = productType, nfcId = nfcId)
        productDao.update(product)
    }
}