package com.example.shoppinghelper.data

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shoppinghelper.auth.LoginViewModel
import com.example.shoppinghelper.products.ProductsViewModel

object ViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                containerApplication().container.googleAuthUiClient
            )
        }
        initializer {
            ProductsViewModel(
                containerApplication().container.googleAuthUiClient,
                containerApplication().container.productDao
            )
        }
    }

    private fun CreationExtras.containerApplication(): ContainerApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ContainerApplication)

}