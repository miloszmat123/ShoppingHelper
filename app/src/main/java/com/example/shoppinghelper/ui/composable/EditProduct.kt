package com.example.shoppinghelper.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinghelper.data.ViewModelProvider
import com.example.shoppinghelper.products.UserProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProduct(
    navigate: () -> Unit,
    currentProductId: Int,
    userProductsViewModel: UserProductsViewModel = viewModel(
        key = "productVMKey",
        factory = ViewModelProvider.Factory
    )
) {
    val user = userProductsViewModel.user
    val products by userProductsViewModel.productsByUserId.collectAsState()
    val currentProduct = products.find { it.id == currentProductId }
    if (user != null && currentProduct != null) {
        var productName by remember { mutableStateOf(currentProduct.productName) }
        var productType by remember { mutableStateOf(currentProduct.productType) }
        val nfcId by remember { mutableStateOf(currentProduct.nfcId) }

        Column {
            TextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("productName") }
            )

            TextField(
                value = productType,
                onValueChange = { productType = it },
                label = { Text("productType") }
            )
            if (nfcId != "") {
                Button(onClick = {
                    userProductsViewModel.updateProduct(
                        user.userId,
                        productName,
                        productType,
                        nfcId
                    )
                    navigate()
                })
                {
                    Text("Save")
                }
            }
        }
    }
}