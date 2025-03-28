package com.hrishi.minichallenges_pl.home_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import kotlinx.serialization.Serializable

@Serializable
data object HomScreenBaseRoute

@Serializable
data object MonthScreenRoute

fun NavController.navigateToHomScreenBaseRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(HomScreenBaseRoute, navOptions)

fun NavController.navigateToMonthScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(MonthScreenRoute, navOptions)