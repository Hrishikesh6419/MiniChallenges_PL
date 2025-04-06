package com.hrishi.minichallenges_pl.months.feb_2025.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import kotlinx.serialization.Serializable

@Serializable
data object Feb2025BaseRoute

@Serializable
data object Feb2025HomeScreenRoute

@Serializable
data object FestiveBatteryIndicatorScreenRoute

@Serializable
data object SmsConfettiScreenRoute

fun NavController.navigateToFeb2025BaseRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(Feb2025BaseRoute, navOptions)

fun NavController.navigateToFeb2025HomeScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(Feb2025HomeScreenRoute, navOptions)

fun NavController.navigateToFestiveBatteryIndicatorScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(FestiveBatteryIndicatorScreenRoute, navOptions)

fun NavController.navigateToSmsConfettiScreenRouteScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(SmsConfettiScreenRoute, navOptions)
