package com.example.shoppinghelper.data

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.shoppinghelper.auth.LoginViewModel

object ViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                containerApplication().container.googleAuthUiClient
            )
        }
    }

    private fun CreationExtras.containerApplication(): ContainerApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ContainerApplication)

}