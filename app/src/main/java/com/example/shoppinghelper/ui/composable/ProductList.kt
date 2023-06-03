package com.example.shoppinghelper.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinghelper.data.ViewModelProvider
import com.example.shoppinghelper.products.ProductsViewModel
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.toList

@Composable
fun ProductList(
    navigate: () -> Unit,
    navigate_add: () -> Unit,
    productsViewModel: ProductsViewModel = viewModel(factory = ViewModelProvider.Factory)
){

    val user = productsViewModel.user
    if (user != null) {
        val products by  productsViewModel.getProducts(user.userId).collectAsState()
        Column() {
            Text(text = user.userId)
            Button(onClick = {
                productsViewModel.signOut()
                navigate()
            }) {
                Text("Logout")
            }
            products.forEach(
                action = {
                    Text(text = it.productName)
                }
            )
            Button(onClick = { navigate_add() }) {
                Text("Add Product")
            }
        }

    }
}