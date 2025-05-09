package com.antoniowalls.indriverstudents.presentation.screens.client.mapSearcher.components

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.antoniowalls.indriverstudents.R
import com.antoniowalls.indriverstudents.presentation.components.DefaultTextField
import com.antoniowalls.indriverstudents.presentation.screens.client.mapSearcher.ClientMapSearcherViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ClientMapSearcherContent(navHostController: NavHostController, paddingValues: PaddingValues, vm: ClientMapSearcherViewModel = hiltViewModel()){
    val context = LocalContext.current
    val placePredictions by vm.placePredictions.collectAsState()
    val selectedPlace by vm.selectedPlace.collectAsState()
    var query by remember { mutableStateOf("") }
    val location by vm.location.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    var isCameraCentered by remember{
        mutableStateOf(false)
    }

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


    Box(

    ){
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            cameraPositionState = cameraPositionState,
            properties = mapProperties
        ){
            location.let{ position ->
                Log.d("ClientMapSearcherContent", "Location: ${position}")
                if(position != null){
                    Marker(
                        state = MarkerState(position = position)
                    )
                }
            }
        }
        Column {
            DefaultTextField(
                modifier = Modifier,
                value = query,
                label = "Recoger en",
                icon = Icons.Default.LocationOn,
                onValueChange = {
                    query = it
                    vm.getPlacePredictions(query)
                }
            )
            LazyColumn {
                items(placePredictions){ prediction ->
                    Text(
                        text= prediction.fullText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{
                                vm.getPlaceDetails(prediction.placeId)
                            }
                    )
                }
            }

            selectedPlace?.let { place ->
                Text(text = "${place.name} ${place.latLng}")
            }
        }
    }

}