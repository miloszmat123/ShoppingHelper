package com.example.shoppinghelper.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinghelper.data.ViewModelProvider
import com.example.shoppinghelper.products.UserProductsViewModel
import com.example.shoppinghelper.tagreader.NFCMethods


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProduct(
    navigate: () -> Unit,
    userProductsViewModel: UserProductsViewModel = viewModel(key = "productVMKey", factory = ViewModelProvider.Factory),
) {
    val nfcMethods = NFCMethods(LocalContext.current)
    val user = userProductsViewModel.user
    if (user != null) {
        var productName by remember { mutableStateOf("") }
        var productType by remember { mutableStateOf("") }
        var nfcId by remember { mutableStateOf("") }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("productName") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 50.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                TextField(
                    value = productType,
                    onValueChange = { productType = it },
                    label = { Text("productType") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Button(onClick = { nfcId = nfcMethods.writeNfcTag() ?: "" }) {
                    Text("Scan NFC")
                }
                if (nfcId != "") {
                    Button(onClick = {
                        userProductsViewModel.addProduct(
                            user.userId,
                            productName,
                            productType,
                            nfcId
                        )
                        navigate()
                    })
                    {
                        Text("Submit")
                    }
                }
            }
            IconButton(
                onClick = { navigate() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }

    }
}