package com.hrishi.minichallenges_pl.home_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hrishi.minichallenges_pl.home_screen.month_screen.MonthScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation<HomScreenBaseRoute>(
        startDestination = MonthScreenRoute
    ) {
        composable<MonthScreenRoute> {
            MonthScreen()
        }
    }
}