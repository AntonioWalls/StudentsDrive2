package com.antoniowalls.indriverstudents.presentation.navigation.graph.client

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.antoniowalls.indriverstudents.presentation.navigation.Graph
import com.antoniowalls.indriverstudents.presentation.navigation.graph.profile.ProfileNavGraph
import com.antoniowalls.indriverstudents.presentation.navigation.screen.auth.AuthScreen
import com.antoniowalls.indriverstudents.presentation.navigation.screen.client.ClientScreen
import com.antoniowalls.indriverstudents.presentation.screens.auth.login.LoginScreen
import com.antoniowalls.indriverstudents.presentation.screens.auth.register.RegisterScreen
import com.antoniowalls.indriverstudents.presentation.screens.client.home.ClientHomeScreen
import com.antoniowalls.indriverstudents.presentation.screens.client.mapSearcher.ClientMapSearcherScreen
import com.antoniowalls.indriverstudents.presentation.screens.profile.info.ProfileInfoScreen
@Composable
fun ClientNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.CLIENT,
        startDestination =ClientScreen.MapSearcher.route
    ){
        composable(route = ClientScreen.MapSearcher.route){ ClientMapSearcherScreen(navHostController = navHostController) }
        ProfileNavGraph(navHostController)
    }
}