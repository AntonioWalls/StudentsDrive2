package com.antoniowalls.indriverstudents.presentation.screens.profile.update

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ProfileUpdateScreen(navHostController: NavHostController, userParam: String) {
    Log.d("ProfileUpdateScreen", "userParam: ${userParam}")
    Scaffold { paddingValues ->
        Text(text = "profile update screen", modifier = Modifier.padding(paddingValues))
    }
}