package com.example.shoppinghelper.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinghelper.data.ViewModelProvider
import com.example.shoppinghelper.products.UserProductsViewModel

@Composable
fun ProductList(
    navigate: () -> Unit,
    navigate_add: () -> Unit,
    navigate_edit: (Int) -> Unit,
    refresh: () -> Unit,
    userProductsViewModel: UserProductsViewModel = viewModel(
        key = "productVMKey",
        factory = ViewModelProvider.Factory
    )
) {
    val user = userProductsViewModel.user
    if (user != null) {
        val products by userProductsViewModel.productsByUserId.collectAsState()
        Column() {
            products.forEach(
                action = {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Product name: ${it.productName}")
                                Text(text = "Product type: ${it.productType}")
                            }

                            Column(
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                IconButton(
                                    onClick = { userProductsViewModel.deleteProduct(it) },
                                    modifier = Modifier.padding(bottom = 4.dp)
                                ) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Delete Product")
                                }

                                IconButton(
                                    onClick = { navigate_edit(it.id) },
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Edit Product")
                                }
                            }
                        }
                    }
                }
            )
            Row(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { navigate_add() }) {
                    Text("Add Product")
                }
                Button(onClick = {
                    userProductsViewModel.signOut()
                    navigate()
                }) {
                    Text("Logout")
                }
            }
        }
    }
    else {
        refresh()
    }
}