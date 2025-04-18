package com.hrishi.minichallenges_pl.months.march_2025.navigation

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

@Serializable
data object OrbitingRocketScreenRoute

@Serializable
data object SpaceCraftFlipCardScreenRoute

@Serializable
data object GravityTiltScreenRoute

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

fun NavController.navigateToOrbitingRocketScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(OrbitingRocketScreenRoute, navOptions)

fun NavController.navigateToSpaceCraftFlipCardScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(SpaceCraftFlipCardScreenRoute, navOptions)

fun NavController.navigateToGravityTiltScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(GravityTiltScreenRoute, navOptions)