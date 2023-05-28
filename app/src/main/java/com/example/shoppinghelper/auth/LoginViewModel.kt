package com.example.shoppinghelper.auth

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(private val googleAuthUiClient: GoogleAuthUiClient) : ViewModel() {
    var loginState by mutableStateOf(googleAuthUiClient.getSignedInUser() != null)
        private set

    fun signIn(launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>) {
        viewModelScope.launch {
            val signInIntentSender = googleAuthUiClient.signIn()
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch
                ).build()
            )
        }
    }

    fun onSignedIn(intent: Intent) {
        viewModelScope.launch {
            val user = googleAuthUiClient.signInWithIntent(intent = intent)
            loginState = user != null
        }
    }
}