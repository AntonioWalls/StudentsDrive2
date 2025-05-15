package com.antoniowalls.indriverstudents.presentation.navigation.graph.roles

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import androidx.navigation.compose.composable
import com.antoniowalls.indriverstudents.presentation.navigation.screen.roles.RolesScreen
import com.antoniowalls.indriverstudents.presentation.screens.roles.RolesScreen
import com.antoniowalls.indriverstudents.presentation.navigation.Graph
import com.antoniowalls.indriverstudents.presentation.screens.client.home.ClientHomeScreen
import com.antoniowalls.indriverstudents.presentation.screens.driver.home.DriverHomeScreen

fun NavGraphBuilder.RolesNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.ROLES,
        startDestination= RolesScreen.Roles.route
    ){
        composable(route = RolesScreen.Roles.route){
            RolesScreen(navHostController = navHostController)
        }
        composable(route = Graph.CLIENT){
            ClientHomeScreen()
        }

        composable(route = Graph.DRIVER){
            DriverHomeScreen()
        }

    }
}