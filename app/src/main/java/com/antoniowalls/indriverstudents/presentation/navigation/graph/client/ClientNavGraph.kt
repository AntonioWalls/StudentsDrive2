package com.antoniowalls.indriverstudents.presentation.navigation.graph.client

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.antoniowalls.indriverstudents.presentation.navigation.Graph
import com.antoniowalls.indriverstudents.presentation.navigation.screen.auth.AuthScreen
import com.antoniowalls.indriverstudents.presentation.navigation.screen.client.ClientScreen
import com.antoniowalls.indriverstudents.presentation.screens.auth.login.LoginScreen
import com.antoniowalls.indriverstudents.presentation.screens.auth.register.RegisterScreen
import com.antoniowalls.indriverstudents.presentation.screens.client.home.ClientHomeScreen

fun NavGraphBuilder.ClientNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.CLIENT,
        startDestination =ClientScreen.Home.route
    ){
        composable(route = ClientScreen.Home.route){ ClientHomeScreen(navHostController = navHostController) }
    }
}