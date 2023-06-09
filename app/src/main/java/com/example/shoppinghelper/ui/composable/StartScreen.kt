package com.example.shoppinghelper.ui.composable

import androidx.compose.runtime.Composable
import java.lang.Thread.sleep


@Composable
fun StartScreen(
    navigate: () -> Unit
){
    sleep(1)
    navigate()
}
