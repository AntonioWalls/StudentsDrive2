package com.antoniowalls.indriverstudents.presentation.screens.profile.info

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ProfileInfoScreen(navhostController: NavHostController) {
    Scaffold() { paddingValues ->
        Text(text = "ProfileInfoScreen", modifier = Modifier.padding(paddingValues))

    }
}