package com.antoniowalls.indriverstudents.presentation.screens.client.mapSearcher.components

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.antoniowalls.indriverstudents.R
import com.antoniowalls.indriverstudents.presentation.components.DefaultButton
import com.antoniowalls.indriverstudents.presentation.components.DefaultTextField
import com.antoniowalls.indriverstudents.presentation.screens.client.mapSearcher.ClientMapSearcherViewModel
import com.antoniowalls.indriverstudents.presentation.util.BitmapDescriptorFromVector
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.libraries.places.api.model.Place
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientMapSearcherContent(navHostController: NavHostController, paddingValues: PaddingValues, vm: ClientMapSearcherViewModel = hiltViewModel()){
    val context = LocalContext.current
    var originQuery by remember { mutableStateOf("") }
    var destinationQuery by remember { mutableStateOf("") }
    var priceQuery by remember { mutableStateOf("") }

    val location by vm.location.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    var isCameraCentered by remember{ mutableStateOf(false) }
    var showSearchModal by remember{ mutableStateOf(false) }
    var showPriceModal by remember{ mutableStateOf(false) }
    var isOriginFocused by remember{ mutableStateOf(false) }
    val originPlace by vm.originPlace.collectAsState()
    var originMarkerPlace by remember{ mutableStateOf<LatLng?>(null) }
    val destinationPlace by vm.destinationPlace.collectAsState()
    val route by vm.route.collectAsState()
    var isMapReady by remember { mutableStateOf(false) }
    var originMarkerDescriptor by remember{ mutableStateOf<BitmapDescriptor?>(null) }
    var destinationMarkerDescriptor by remember{ mutableStateOf<BitmapDescriptor?>(null) }
    val mapProperties by remember {
        mutableStateOf(MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style), //aquí le asignamos un tema personalizado a nuestro mapa
            isMyLocationEnabled = true
        )
        )
    }

    LaunchedEffect(key1 = route) {
        if(route != null){
            originMarkerPlace = originPlace?.latLng
        }
    }

    LaunchedEffect(key1 = isMapReady) {
        originMarkerDescriptor = context.BitmapDescriptorFromVector(R.drawable.pin_map_128, 128, 128)
        destinationMarkerDescriptor = context.BitmapDescriptorFromVector(R.drawable.flag_128, 128, 128)
    }

    LaunchedEffect(key1 = originPlace) {
        originPlace?.let { place ->
            originQuery = place.address ?: ""

        }
    }

    LaunchedEffect(key1 = location) {
        if(location !=null && !isCameraCentered){
            cameraPositionState.position = CameraPosition.fromLatLngZoom(location!!, 14f)
            isCameraCentered = true
        }
    }


    BottomSheetScaffold(
        sheetContent = {
            AnimatedVisibility(visible = !vm.isInteractingWithMap) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(calculateSheetHeight(vm = vm))
                        .background(Color.White)
                ) {
                    Card(
                        modifier = Modifier
                            .padding(top = 15.dp, start = 30.dp, end = 30.dp)
                            .clickable{
                                isOriginFocused = true
                                showSearchModal = true
                            },
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFEEEEEE)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(30.dp).padding(start = 5.dp),
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = originQuery.ifEmpty { "Recoger en" }

                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .padding( start = 30.dp, end = 30.dp)
                            .clickable{
                                isOriginFocused = false
                                showSearchModal = true
                            },
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFEEEEEE)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(30.dp).padding(start = 5.dp),
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = destinationQuery.ifEmpty { "Destino" }
                            )
                        }
                    }
                    //pal precio
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .padding( start = 30.dp, end = 30.dp)
                            .clickable{
                                showPriceModal = true
                            },
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFEEEEEE)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(30.dp).padding(start = 5.dp),
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = priceQuery.ifEmpty { "Ofertar precio" }
                            )
                        }
                    }
                    DefaultButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp, top = 20.dp),
                        text = "Buscar conductor",
                        onClick = { /* ... */ },
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )

                }
            }
        },
        scaffoldState = rememberBottomSheetScaffoldState(),
        sheetPeekHeight = calculateSheetHeight(vm = vm),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                Box(){
                    GoogleMap(
                        modifier = Modifier
                            .fillMaxHeight(
                                fraction= if(vm.isInteractingWithMap) 0.95f else 0.6f
                            )
                            .padding(paddingValues),
                        cameraPositionState = cameraPositionState,
                        properties = mapProperties,
                        onMapLoaded = {
                            isMapReady = true
                        }
                    ){
                        route?.let { routePoints ->
                            Polyline(
                                points = routePoints,
                                color = Color.Cyan,
                                width = 15f
                            )
                            if(originPlace?.latLng != null && destinationPlace?.latLng != null){
                                if(originMarkerPlace != null){
                                    Marker(
                                        icon = originMarkerDescriptor,
                                        state = MarkerState(position = originMarkerPlace!!)
                                    )
                                }
                                 Marker(
                                icon = destinationMarkerDescriptor,
                                state = MarkerState(position = destinationPlace?.latLng!!)
                                 )
                            }
                        }
                        /*location.let{ position ->
                            Log.d("ClientMapSearcherContent", "Location: ${position}")
                            if(position != null){
                                Marker(
                                    state = MarkerState(position = position)
                                )
                            }*/
                        }
                    }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 128.dp), // Este padding mueve el pin hacia arriba
                    contentAlignment = Alignment.Center
                ) {
                    if(originMarkerPlace==null){
                        Icon(
                            modifier = Modifier.size(50.dp),
                            painter = painterResource(id = R.drawable.pin),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                 }
                }

                checkForMapInteraction(cameraPositionState = cameraPositionState, vm=vm)
                if(showSearchModal){
                    PlaceSearchModal(
                        onDissmissRequest = {showSearchModal=false},
                        onPlaceSelected = {place ->
                            if(isOriginFocused){
                                originQuery = place.address
                                cameraPositionState.position = CameraPosition.fromLatLngZoom(place.latLng!!, 14f)
                            }else{
                                destinationQuery = place.address
                            }
                            if(!originQuery.isNullOrBlank() && !destinationQuery.isNullOrBlank()){
                                vm.getRoute()
                            }
                            showSearchModal = false
                        },
                        isOriginFocused = isOriginFocused,
                        vm = vm
                    )
                }

                //validador de precio
                if(showPriceModal){
                    PriceModal(
                        onDissmissRequest = {showSearchModal=false},
                        onPriceSelected = {price ->
                            priceQuery = price
                            showPriceModal = false
                        }
                    )
                }
        }
    )



}

@Composable
private fun PlaceSearchModal(
    onDissmissRequest: () -> Unit,
    onPlaceSelected: (place: Place) -> Unit,
    isOriginFocused: Boolean,
    vm: ClientMapSearcherViewModel
){
    val placePredictions by vm.placePredictions.collectAsState()
    var originSearchQuery by remember { mutableStateOf("") }
    var destinationSearchQuery by remember { mutableStateOf("") }
    val searchQueryState = remember { mutableStateOf("") }
    val originFocusRequester = remember { FocusRequester() }
    val destFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(isOriginFocused) {
        withFrameNanos {
            if (isOriginFocused) originFocusRequester.requestFocus()
            else destFocusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    Dialog(
        onDismissRequest = onDissmissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        // Aquí está el cambio principal: aplicamos el clip al Box exterior
        // y movemos el background al Column interior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.75f)
                .clip(
                    RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp
                    )
                )
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White) // Movemos el background aquí
            ) {
                // Encabezado negro - sin cambios
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(Color.Black),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.padding(start = 30.dp),
                        text="INTRODUCE TU RUTA",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    IconButton(
                        modifier = Modifier.padding(end = 10.dp),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White),
                        onClick= onDissmissRequest
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }

                // Espacio después del encabezado
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de origen
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth(),
                        focusRequester = originFocusRequester,
                        useFocusTarget = true,
                        value = originSearchQuery,
                        label = "Recoger en",
                        icon = Icons.Default.LocationOn,
                        onValueChange = {
                            originSearchQuery = it
                            if(isOriginFocused){
                                searchQueryState.value = it
                            }
                        },
                        enabled = isOriginFocused,
                        background = Color(0xFFF2F2F2)
                    )
                }

                // Espacio entre campos
                Spacer(modifier = Modifier.height(8.dp))

                // Campo de destino
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth(),
                        focusRequester = destFocusRequester,
                        useFocusTarget = true,
                        value = destinationSearchQuery,
                        label = "Destino",
                        icon = Icons.Default.LocationOn,
                        onValueChange = {
                            destinationSearchQuery = it
                            if(!isOriginFocused){
                                searchQueryState.value = it
                            }
                        },
                        enabled = !isOriginFocused,
                        background = Color(0xFFF2F2F2)
                    )
                }

                // Espacio antes de la lista
                Spacer(modifier = Modifier.height(8.dp))

                // Lista de predicciones
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    items(placePredictions){ prediction ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    vm.getPlaceDetails(prediction.placeId, isOriginFocused) { place ->
                                        onPlaceSelected(place)
                                    }
                                }
                                .padding(vertical = 12.dp, horizontal = 8.dp)
                        ) {
                            Text(
                                text = prediction.fullText,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                        HorizontalDivider(
                            color = Color(0xFFEEEEEE),
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(searchQueryState.value) {
        if(searchQueryState.value.isNotEmpty()){
            delay(500)
            if(searchQueryState.value == if(isOriginFocused) originSearchQuery else destinationSearchQuery){
                vm.getPlacePredictions(searchQueryState.value)
            }
        }
    }
}

@Composable
private fun PriceModal(
    onDissmissRequest: () -> Unit,
    onPriceSelected: (string: String) -> Unit
){
    var priceQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        withFrameNanos {
            focusRequester.requestFocus()
        }
    }

    Dialog(
        onDismissRequest = onDissmissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        // Aquí está el cambio principal: aplicamos el clip al Box exterior
        // y movemos el background al Column interior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.75f)
                .clip(
                    RoundedCornerShape(
                        topStart = 30.dp,
                        topEnd = 30.dp
                    )
                )
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White) // Movemos el background aquí
            ) {
                // Encabezado negro - sin cambios
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(Color.Black),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.padding(start = 30.dp),
                        text="INTRODUCE TU OFERTA",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    IconButton(
                        modifier = Modifier.padding(end = 10.dp),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White),
                        onClick= onDissmissRequest
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }

                // Espacio después del encabezado
                Spacer(modifier = Modifier.height(16.dp))

                // Espacio entre campos
                Spacer(modifier = Modifier.height(8.dp))

                // Campo de destino
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    DefaultTextField(
                        modifier = Modifier.fillMaxWidth(),
                        focusRequester = focusRequester,
                        useFocusTarget = true,
                        value = priceQuery,
                        label = "Valor de oferta",
                        icon = Icons.Default.Info,
                        onValueChange = {
                            priceQuery = it
                        },
                        keyboardType = KeyboardType.Number,
                        background = Color(0xFFF2F2F2)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                DefaultButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, top = 20.dp),
                    text = "Asignar precio",
                    onClick = {
                        onPriceSelected(priceQuery)
                        onDissmissRequest()
                              },
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )

                // Espacio antes de la lista
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }

}


@Composable
private fun calculateSheetHeight(vm: ClientMapSearcherViewModel): Dp{
    val normalHeight = LocalConfiguration.current.screenHeightDp.dp *0.4f
    val minimizedHeight = 50.dp
    return animateDpAsState(
        if(vm.isInteractingWithMap)minimizedHeight else normalHeight,
        animationSpec = spring(stiffness = 300f)
    ).value

}

@Composable
private fun checkForMapInteraction(
    cameraPositionState: CameraPositionState,
    vm: ClientMapSearcherViewModel
){
    var initialCameraPosition by remember { mutableStateOf(cameraPositionState.position) }
    val onMapCameraMoveStart: (cameraPosition: CameraPosition) -> Unit = { newPosition ->
        initialCameraPosition = newPosition

        vm.isInteractingWithMap = true
    }

    val onMapCameraIdle: (cameraPosition: CameraPosition) -> Unit = { newCameraPosition ->
        val cameraMovementReason = cameraPositionState.cameraMoveStartedReason
    /*    if(newCameraPosition.zoom < initialCameraPosition.zoom){
            vm.isInteractingWithMap = false
        }
        if(newCameraPosition.zoom > initialCameraPosition.zoom){
            vm.isInteractingWithMap = false
        }
        if(newCameraPosition.bearing != initialCameraPosition.bearing){
            vm.isInteractingWithMap = false
        }*/
        if(cameraMovementReason == CameraMoveStartedReason.GESTURE){
            if(newCameraPosition.target != initialCameraPosition.target){
                //vm.isInteractingWithMap = false
                if(vm.route.value==null){
                    vm.getPlaceFromLatLng(newCameraPosition.target)
                }
            }
        }
        initialCameraPosition = newCameraPosition
    }
    LaunchedEffect(key1 = cameraPositionState.isMoving) {
        vm.isInteractingWithMap = cameraPositionState.isMoving
        onMapCameraIdle(cameraPositionState.position)
        //if(cameraPositionState.isMoving) onMapCameraMoveStart(cameraPositionState.position) else onMapCameraIdle(cameraPositionState.position)
    }
}
