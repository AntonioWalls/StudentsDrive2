package com.antoniowalls.indriverstudents.presentation.screens.driver.mapMyLocation.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.antoniowalls.indriverstudents.R
import com.antoniowalls.indriverstudents.presentation.screens.driver.mapMyLocation.DriverMapMyLocationViewModel
import com.antoniowalls.indriverstudents.presentation.util.BitmapDescriptorFromVector
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.MarkerState.Companion.invoke
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DriverMapMyLocationContent(paddingValues: PaddingValues, navHostController: NavHostController, vm: DriverMapMyLocationViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val location by vm.location.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    var isCameraCentered by remember{ mutableStateOf(false) }
    var myLocationMarkerDescriptor by remember{ mutableStateOf<BitmapDescriptor?>(null) }
    var isMapReady by remember { mutableStateOf(false) }

    val mapProperties by remember {
        mutableStateOf(MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style), //aquí le asignamos un tema personalizado a nuestro mapa
            isMyLocationEnabled = true
        )
        )
    }

    LaunchedEffect(key1 = location) {
        if(location !=null && !isCameraCentered){
            cameraPositionState.position = CameraPosition.fromLatLngZoom(location!!, 14f)
            isCameraCentered = true
        }
    }

    LaunchedEffect(key1 = isMapReady) {
        if(isMapReady){
            myLocationMarkerDescriptor = context.BitmapDescriptorFromVector(R.drawable.location_blue, 128, 128)
        }
    }

    Box(){
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onMapLoaded = {
                isMapReady = true
            }
        ){
            location.let{ position ->
                Log.d("ClientMapSearcherContent", "Location: ${position}")
                if(position != null){
                    Marker(
                        state = MarkerState(position = position),
                        icon = myLocationMarkerDescriptor,
                    )
                }
            }
        }
    }
}