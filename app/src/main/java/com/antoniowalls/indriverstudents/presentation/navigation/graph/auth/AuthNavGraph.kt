package com.antoniowalls.indriverstudents.presentation.navigation.graph.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.antoniowalls.indriverstudents.presentation.navigation.Graph
import com.antoniowalls.indriverstudents.presentation.navigation.graph.client.ClientNavGraph
import com.antoniowalls.indriverstudents.presentation.navigation.screen.auth.AuthScreen
import com.antoniowalls.indriverstudents.presentation.screens.auth.login.LoginScreen
import com.antoniowalls.indriverstudents.presentation.screens.auth.register.RegisterScreen
import com.antoniowalls.indriverstudents.presentation.screens.client.home.ClientHomeScreen

fun NavGraphBuilder.AuthNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.AUTH,
        startDestination =AuthScreen.Login.route
    ){
        composable(route = AuthScreen.Login.route){ LoginScreen(navHostController) }
        composable(route = AuthScreen.Register.route){ RegisterScreen(navHostController) }
        composable(route = Graph.CLIENT){ ClientHomeScreen() }

    }
}