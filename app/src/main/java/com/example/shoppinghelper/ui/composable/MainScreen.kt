package com.example.shoppinghelper.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinghelper.R
import com.example.shoppinghelper.data.ViewModelProvider
import com.example.shoppinghelper.products.ProductsViewModel
import com.example.shoppinghelper.tagreader.NFCMethods
import com.example.shoppinghelper.tagreader.TextReader

@Composable
fun MainScreen(
    productsViewModel: ProductsViewModel = viewModel(factory = ViewModelProvider.Factory)
) {

    val nfcMethods = NFCMethods(LocalContext.current)
    val textReader = TextReader(LocalContext.current)
    val products by productsViewModel.products.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.main_screen_text),
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Button(
                onClick = {
                    val nfcId = nfcMethods.readNfcTag()?.substring(3)
                    val product = products.firstOrNull { it.nfcId == nfcId }
                    if (product != null) {
                        val message =
                            "Product name is ${product.productName} and product type is ${product.productType}"
                        textReader.speakText(message)
                    }
                },
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .padding(16.dp)
                    .size(400.dp),
            ) {
                Text(
                    text = "NFC",
                    fontSize = 100.sp,
                    fontStyle = FontStyle.Normal
                )
            }
        }

    }
}
