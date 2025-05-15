package com.antoniowalls.indriverstudents.presentation.screens.driver.mapMyLocation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.antoniowalls.indriverstudents.presentation.screens.client.mapSearcher.components.ClientMapSearcherContent
import com.antoniowalls.indriverstudents.presentation.screens.driver.mapMyLocation.components.DriverMapMyLocationContent

@Composable
fun DriverMapMyLocationScreen(navHostController: NavHostController, vm: DriverMapMyLocationViewModel = hiltViewModel())  {
    val context = LocalContext.current
    var hasPermission by remember{
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasPermission = isGranted
            if(isGranted){
                vm.startLocationUpdates()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            vm.startLocationUpdates()
        }

    }

    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars
    ){ paddingValues ->
        if(hasPermission){
            DriverMapMyLocationContent(paddingValues = paddingValues, navHostController = navHostController)
        }else{
            Text(text = "Sin permisos, habilita los permisos de ubicación en la configuración de tu telefono")

        }

    }
}