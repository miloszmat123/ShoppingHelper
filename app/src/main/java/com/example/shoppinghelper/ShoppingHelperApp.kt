package com.example.shoppinghelper


import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shoppinghelper.ui.composable.MainScreen
import com.example.shoppinghelper.ui.composable.AdminPanel


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
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            val showBackButton = currentRoute == Screens.AdminPanel.name

            TopAppBar(
                title = { Text(text = currentRoute.toString()) },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = { navController.navigate(Screens.Main.name) }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    if (!showBackButton) {
                        IconButton(
                            onClick = {
                                navController.navigate(Screens.AdminPanel.name)
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
            startDestination = Screens.Main.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screens.Main.name) {
                MainScreen(navController = navController)
            }
            composable(route = Screens.AdminPanel.name) {
                AdminPanel(navController = navController)
            }
        }
    }
}

