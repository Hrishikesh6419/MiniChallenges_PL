package com.hrishi.minichallenges_pl.march_2025.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import kotlinx.serialization.Serializable

@Serializable
data object March2025BaseRoute

@Serializable
data object March2025HomeScreenRoute

@Serializable
data object DawnDuskTransitionScreenRoute

@Serializable
data object MarsWeatherCardScreenRoute

fun NavController.navigateToMarch2025BaseRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(March2025BaseRoute, navOptions)

fun NavController.navigateToMarch2025HomeScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(March2025HomeScreenRoute, navOptions)

fun NavController.navigateToDawnDuskTransitionScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(DawnDuskTransitionScreenRoute, navOptions)

fun NavController.navigateToMarsWeatherCardScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(MarsWeatherCardScreenRoute, navOptions)