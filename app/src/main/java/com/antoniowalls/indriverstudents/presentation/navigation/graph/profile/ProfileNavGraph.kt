package com.antoniowalls.indriverstudents.presentation.navigation.graph.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.antoniowalls.indriverstudents.presentation.navigation.Graph
import com.antoniowalls.indriverstudents.presentation.navigation.graph.client.ClientNavGraph
import com.antoniowalls.indriverstudents.presentation.navigation.screen.auth.AuthScreen
import com.antoniowalls.indriverstudents.presentation.navigation.screen.profile.ProfileScreen
import com.antoniowalls.indriverstudents.presentation.screens.auth.login.LoginScreen
import com.antoniowalls.indriverstudents.presentation.screens.auth.register.RegisterScreen
import com.antoniowalls.indriverstudents.presentation.screens.client.home.ClientHomeScreen
import com.antoniowalls.indriverstudents.presentation.screens.profile.info.ProfileInfoScreen
import com.antoniowalls.indriverstudents.presentation.screens.profile.update.ProfileUpdateScreen

fun NavGraphBuilder.ProfileNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.PROFILE,
        startDestination =ProfileScreen.ProfileInfo.route
    ){
        composable(route = ProfileScreen.ProfileInfo.route){ ProfileInfoScreen(navHostController) }
        composable(
            route = ProfileScreen.ProfileUpdate.route,
            arguments = listOf(navArgument ("user"){
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString("user")?.let{
                ProfileUpdateScreen(navHostController = navHostController, userParam = it)
            }
        }


    }
}