package com.hrishi.minichallenges_pl.months.april_2025.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import kotlinx.serialization.Serializable

@Serializable
data object April2025BaseRoute

@Serializable
data object April2025HomeScreenRoute

@Serializable
data object ShakyEggScreenRoute

@Serializable
data object EggHuntChecklistScreenRoute

fun NavController.navigateToApril2025BaseRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(April2025BaseRoute, navOptions)

fun NavController.navigateToApril2025HomeScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(April2025HomeScreenRoute, navOptions)

fun NavController.navigateToShakyEggScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(ShakyEggScreenRoute, navOptions)

fun NavController.navigateToEggHuntChecklistScreenRoute(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(EggHuntChecklistScreenRoute, navOptions)
