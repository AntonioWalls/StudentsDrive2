package com.antoniowalls.indriverstudents.presentation.navigation.screen.client

sealed class ClientScreen(val route: String) {
    object MapSearcher: ClientScreen("/client/mapSearcher")
}