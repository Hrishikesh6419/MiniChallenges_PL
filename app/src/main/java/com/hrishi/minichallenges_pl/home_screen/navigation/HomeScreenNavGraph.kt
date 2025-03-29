package com.hrishi.minichallenges_pl.home_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hrishi.minichallenges_pl.core.utils.Months
import com.hrishi.minichallenges_pl.feb_2025.navigation.navigateToFeb2025BaseRoute
import com.hrishi.minichallenges_pl.home_screen.month_screen.MonthScreen
import com.hrishi.minichallenges_pl.march_2025.navigation.navigateToMarch2025BaseRoute

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {
    navigation<HomScreenBaseRoute>(
        startDestination = MonthScreenRoute
    ) {
        composable<MonthScreenRoute> {
            MonthScreen(
                onMonthSelected = { month ->
                    when (month) {
                        Months.FEB_2025 -> navController.navigateToFeb2025BaseRoute()
                        Months.MAR_2025 -> navController.navigateToMarch2025BaseRoute()
                    }
                }
            )
        }
    }
}