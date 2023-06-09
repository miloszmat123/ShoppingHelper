package com.example.shoppinghelper.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinghelper.data.ViewModelProvider
import com.example.shoppinghelper.products.UserProductsViewModel
import com.example.shoppinghelper.tagreader.NFCMethods


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProduct(
    navigate: () -> Unit,
    userProductsViewModel: UserProductsViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val nfcMethods = NFCMethods(LocalContext.current)
    val user = userProductsViewModel.user
    if (user != null) {
        var productName by remember { mutableStateOf("") }
        var productType by remember { mutableStateOf("") }
        var nfcId by remember { mutableStateOf("") }

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

            Button(onClick = {
                    nfcId = nfcMethods.writeNfcTag() ?: ""
            }) {
                Text("Scan NFC")
            }
            if( nfcId != "") {
                Button(onClick = {
                    userProductsViewModel.addProduct(user.userId, productName, productType, nfcId)
                    navigate()
                })
                {
                    Text("Submit")
                }
            }

        }
    }
}