package com.example.shoppinghelper.data

import android.content.Context
import com.example.shoppinghelper.auth.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity



class AppContainer(private val context: Context) {

    val googleAuthUiClient: GoogleAuthUiClient by lazy {
        GoogleAuthUiClient(context, Identity.getSignInClient(context))
    }

    val productDao: ProductDao by lazy {
        ShoppingHelperDatabase.getDatabase(context).productDao()
    }
}