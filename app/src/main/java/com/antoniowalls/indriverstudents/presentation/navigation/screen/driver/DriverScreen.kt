package com.antoniowalls.indriverstudents.presentation.navigation.screen.driver

sealed class DriverScreen(val route: String) {
    object MapMyLocation: DriverScreen("/driver/map/my_location")
}