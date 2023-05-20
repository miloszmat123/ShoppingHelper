package com.example.shoppinghelper


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinghelper.ui.composable.MainScreen


enum class Screens(@StringRes val title: Int) {
    Main(title = R.string.app_name),
    AdminPanel(title = R.string.admin_panel),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingHelperApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Shopping Helper") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Main.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.Main.name) {
                MainScreen()
            }
            composable(route = Screens.AdminPanel.name) {
            }
        }
    }
}
