package com.example.shoppinghelper


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shoppinghelper.data.ViewModelProvider
import com.example.shoppinghelper.products.UserProductsViewModel
import com.example.shoppinghelper.ui.composable.AddProduct
import com.example.shoppinghelper.ui.composable.EditProduct
import com.example.shoppinghelper.ui.composable.LoginGooglePanel
import com.example.shoppinghelper.ui.composable.MainScreen
import com.example.shoppinghelper.ui.composable.ProductList
import com.example.shoppinghelper.ui.composable.StartScreen


enum class Screens(@StringRes val title: Int) {
    Main(title = R.string.app_name),
    LoginPanel(title = R.string.login_panel),
    ProductList(title = R.string.product_list),
    AddProduct(title = R.string.add_product),
    EditProduct(title = R.string.edit_product),
    StartScreen(title = R.string.start_screen);
    companion object {
        fun valueFromString(name: String): Int {
            return when(name){
                "Main" -> Main.title
                "LoginPanel" -> LoginPanel.title
                "ProductList" -> ProductList.title
                "AddProduct" -> AddProduct.title
                "StartScreen" -> StartScreen.title
                else -> {R.string.edit_product}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingHelperApp(
    navController: NavHostController = rememberNavController()
) {
    val userProductsViewModel: UserProductsViewModel =
        viewModel(factory = ViewModelProvider.Factory)
    val refresh = {
        userProductsViewModel.refresh()}
    Scaffold(
        topBar = {
            val currentRoute =
                navController.currentBackStackEntryAsState().value?.destination?.route
            val showNFCButton = currentRoute == Screens.LoginPanel.name ||
                    currentRoute == Screens.ProductList.name ||
                    currentRoute == Screens.AddProduct.name ||
                    currentRoute == Screens.EditProduct.name


            TopAppBar(
                title = { Text(text = stringResource(Screens.valueFromString(currentRoute.toString()))) },
                actions = {
                    if (showNFCButton) {
                        IconButton(
                            onClick = {
                                navController.navigate(Screens.Main.name)
                            }
                        ) {
                            Icon(Icons.Filled.Nfc, contentDescription = "NFC Reader")
                        }
                    }
                    if (!showNFCButton) {
                        IconButton(
                            onClick = {
                                navController.navigate(Screens.LoginPanel.name)
                            }
                        ) {
                            Icon(Icons.Filled.AccountCircle, contentDescription = "Admin Panel")
                        }
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.StartScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(route = Screens.StartScreen.name) {
                StartScreen(
                    navigate = { navController.navigate(Screens.Main.name) }
                )
            }
            composable(route = Screens.Main.name) {
                MainScreen()
            }
            composable(route = Screens.LoginPanel.name) {
                LoginGooglePanel(
                    navigate = {
                        navController.navigate(Screens.ProductList.name) {
                            popUpTo(Screens.ProductList.name) { inclusive = true }
                        }
                    },
                    refresh = refresh
                )
            }
            composable(route = Screens.ProductList.name) {
                ProductList(
                    navigate = {
                        navController.navigate(Screens.Main.name)
                    },
                    navigate_add = { navController.navigate(Screens.AddProduct.name) },
                    navigate_edit = { productId: Int -> navController.navigate("${Screens.EditProduct.name}/$productId") },
                    refresh = refresh,
                    userProductsViewModel
                )
            }
            composable(route = Screens.AddProduct.name) {
                AddProduct(
                    navigate = { navController.navigate(Screens.ProductList.name) },
                    userProductsViewModel
                )
            }
            composable(route = "${Screens.EditProduct.name}/{productId}") { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val productId = arguments.getString("productId")?.toIntOrNull()
                if (productId != null) {
                    EditProduct(
                        navigate = { navController.navigate(Screens.ProductList.name) },
                        productId,
                        userProductsViewModel
                    )
                }
            }

        }
    }
}

